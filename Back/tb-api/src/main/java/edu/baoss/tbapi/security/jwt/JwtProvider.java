package edu.baoss.tbapi.security.jwt;

import edu.baoss.tbapi.exceptions.TokenValidationException;
import edu.baoss.tbapi.model.Role;
import edu.baoss.tbapi.model.User;
import edu.baoss.tbapi.services.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
@Slf4j
public class JwtProvider {
    @Value("${jwt.token.secret}")
    private String secretKey;

    @Value("${jwt.token.expired}")
    private long validityTime;

    @Value("${jwt.token.secondPause}")
    private long secondPause;

    @Autowired
    private UserService userService;

    public JwtProvider(){}

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String login, Role role) {
        User user = userService.getUserByLogin(login);
        Claims claims = Jwts.claims().setSubject(login);
        claims.put("role", role);
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityTime);
        if (user.getMinRefreshDate() == null) {
            userService.setMinRefreshDate(login, new Date(now.getTime() - secondPause));
        }
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        final User user = userService.getUserByLogin(claims.getSubject());
        if (claims.getIssuedAt().compareTo(user.getMinRefreshDate()) < 0) {
            throw new TokenValidationException("Expired or invalid JWT token", HttpStatus.UNAUTHORIZED);
        }
        return true;
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

}

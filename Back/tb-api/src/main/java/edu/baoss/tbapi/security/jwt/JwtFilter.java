package edu.baoss.tbapi.security.jwt;

import edu.baoss.tbapi.exceptions.TokenValidationException;
import edu.baoss.tbapi.services.UserService;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        attemptAuthentication(httpServletRequest, httpServletResponse, filterChain);
        String token = jwtProvider.resolveToken(httpServletRequest);
        try {
            if (token != null && jwtProvider.validateToken(token)) {
                //httpServletResponse.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
                Authentication auth = getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (TokenValidationException ex) {
            SecurityContextHolder.clearContext();
            //httpServletResponse.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
            //httpServletResponse.setHeader("Access-Control-Allow-Origin", "https://netbooksfront.herokuapp.com");
            httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
            httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
            httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
            httpServletResponse.setHeader("Access-Control-Allow-Headers", "X-Requested-With, " +
                    "Content-Type, Authorization, Origin, Accept, Access-Control-Request-Method, " +
                    "Access-Control-Request-Headers");

            httpServletResponse.sendError(ex.getHttpStatus().value(), ex.getMessage());
            return;
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }


    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userService.loadUserByUsername(jwtProvider.getUsername(token));
        System.out.println(userDetails);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private void attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        //httpServletResponse.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        //httpServletResponse.setHeader("Access-Control-Allow-Origin", "https://netbooksfront.herokuapp.com");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
        httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "X-Requested-With, " +
                "Content-Type, Authorization, Origin, Accept, Access-Control-Request-Method, " +
                "Access-Control-Request-Headers");
    }
}

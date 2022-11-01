package edu.baoss.tbapi.controllers;


import edu.baoss.tbapi.model.User;
import edu.baoss.tbapi.security.jwt.JwtProvider;
import edu.baoss.tbapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private UserService userService;

    @PostMapping("/sign-in")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> signIn(@RequestBody User user) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getLogin(),
                        user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User fullUser = userService.getUserByLogin(user.getLogin());
        System.out.println("Full: "+fullUser);
        String token = jwtProvider.createToken(fullUser.getLogin(), fullUser.getRole());
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("id", fullUser.getId());
        response.put("login", user.getLogin());
        response.put("role", fullUser.getRole().name());
        System.out.println(response);
        return ResponseEntity.ok(response);
    }
}

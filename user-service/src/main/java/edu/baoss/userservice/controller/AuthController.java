package edu.baoss.userservice.controller;

import edu.baoss.userservice.dto.UserDto;
import edu.baoss.userservice.model.User;
import edu.baoss.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    UserService userService;

    @GetMapping("/get-user-by-login")
    public UserDto getUserByLogin(@RequestParam("login") String login){
        return userService.getUserByLogin(login);
    }

    @GetMapping("/test")
    public String test(){
        return "srfr";
    }

    @PutMapping("/update-min-refresh-date")
    public ResponseEntity<UserDto> updateMinRefreshDate(@RequestParam("login") String login,
                                                     @RequestParam("date")
                                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)Date date){
        return ResponseEntity.ok(userService.updateMinRefreshDate(login, date));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<UserDto> signUp(@RequestBody UserDto user) {
        return ResponseEntity.ok(userService.register(user));
    }

}

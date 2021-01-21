package edu.baoss.tbapi.feignproxies;


import edu.baoss.tbapi.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@FeignClient(name="user-service", url="localhost:5555/user-service/auth")
public interface UserFeignProxy {
    @GetMapping("/get-user-by-login")
    User getUserByLogin(@RequestParam("login") String login);

    @PostMapping("/register")
    ResponseEntity<User> register(@RequestBody User user);

    @PutMapping("/update-min-refresh-date")
    ResponseEntity<User> setMinRefreshDateByLogin(@RequestParam("login") String login,
                                                  @RequestParam("date")
                                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date);
}

package edu.baoss.userservice.controller;

import edu.baoss.userservice.dto.UserAddressDto;
import edu.baoss.userservice.model.Address;
import edu.baoss.userservice.model.User;
import edu.baoss.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/user-info")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{login}")
    public User getUserByLogin(@PathVariable("login") String login){
        return userService.getUserByLogin(login);
    }

    @PostMapping("/user-address")
    public User addOrDeleteAddressForUser(@RequestBody UserAddressDto userAddressDto){
        return userService.addOrDeleteAddressForUser(userAddressDto.getUser(), userAddressDto.getAddress());
    }

    @PutMapping("/update-user")
    public User updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }

    @GetMapping("/addresses")
    public Set<Address> getAddressesByLogin(@RequestParam String login) {
        return userService.getUserByLogin(login).getAddresses();
    }
}

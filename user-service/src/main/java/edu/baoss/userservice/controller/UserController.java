package edu.baoss.userservice.controller;

import edu.baoss.userservice.dto.AddressDto;
import edu.baoss.userservice.dto.UserAddressDto;
import edu.baoss.userservice.dto.UserDto;
import edu.baoss.userservice.model.Address;
import edu.baoss.userservice.model.User;
import edu.baoss.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping
    public List<UserDto> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{login}")
    public UserDto getUser(@PathVariable String login){
        return userService.getUserByLogin(login);
    }

//    @GetMapping("/{login}")
//    public UserDto getUserByLogin(@PathVariable("login") String login){
//        return userService.getUserByLogin(login);
//    }

    @PostMapping("/user-address")
    public UserDto addOrDeleteAddressForUser(@RequestBody UserAddressDto userAddressDto){
        return userService.addAddressForUser(new User(userAddressDto.getUser()), userAddressDto.getAddress(), true);
    }

    @DeleteMapping("/{userId}/address/{addressId}")
    public UserDto deleteAddressForUser(@PathVariable long userId, @PathVariable long addressId) {
        return userService.deleteAddressForUser(userId, addressId);
    }

    @PutMapping
    public UserDto updateUser(@RequestBody UserDto userDto){
        return userService.updateUser(userDto);
    }

    @GetMapping("/addresses")
    public Set<AddressDto> getAddressesByLogin(@RequestParam String login) {
        return userService.getUserByLogin(login).getAddresses();
    }
}

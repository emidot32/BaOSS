package edu.baoss.userservice.controller;

import edu.baoss.userservice.model.User;
import edu.baoss.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/user-info")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable long id){
        return userRepository.findById(id).get();
    }

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }
}

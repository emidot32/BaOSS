package edu.baoss.tbapi.services;

import edu.baoss.tbapi.feignproxies.UserFeignProxy;
import edu.baoss.tbapi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service(value = "userService")
public class UserService implements UserDetailsService {
//    @Autowired
//    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserFeignProxy userFeignProxy;

//    public ResponseEntity<?> registerUser(User user){
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        return userFeignProxy.register(user);
//    }

    public void setMinRefreshDate(String login, Date date) {
        userFeignProxy.setMinRefreshDateByLogin(login, date);
    }

    public User getUserByLogin(String login){
        return userFeignProxy.getUserByLogin(login);
    }

    public UserDetails loadUserByUsername(String login) throws
            UsernameNotFoundException {
        User user = userFeignProxy.getUserByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid login or password");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), user.getAuthorities());
    }
}

package edu.baoss.userservice.service;

import com.google.common.collect.Lists;
import edu.baoss.userservice.exceptions.*;
import edu.baoss.userservice.model.Address;
import edu.baoss.userservice.model.Role;
import edu.baoss.userservice.model.User;
import edu.baoss.userservice.repository.AddressRepository;
import edu.baoss.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    ValidationService validationService;
    @Autowired
    PasswordEncoder encoder;

    public User getUserByLogin(String login){
        return userRepository.getUserByLogin(login);
    }

    public User updateMinRefreshDate(String login, Date date) {
        User user = getUserByLogin(login);
        if (user == null) throw new UserNotFoundException("User with login '"+login+"' is not found");
        user.setMinRefreshDate(date);
        return userRepository.save(user);
    }

    public User register(User user){
        if (userRepository.existsUserByLogin(user.getLogin())){
            throw new LoginExistException("Login is already in use");
        }
        if (userRepository.existsUserByEmail(user.getEmail())){
            throw new EmailExistException("Email is already in use");
        }
        if (userRepository.existsUserByIdCardNumber(user.getIdCardNumber())){
            throw new IdCardNumberExistException("Id card number is already in use");
        }
        if (!validationService.userValidation(user)){
            throw new ValidationException("Invalid login, password or id card number");
        }
        user.setRole(Role.USER);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setIdCardNumber(encoder.encode(user.getIdCardNumber()));
        user.setContractNumber(userRepository.getMaxContractNumber()+1);
        user.setRegDate(new Date());
        user.setActivityStatus(true);

        Set<Address> oldAddresses = new HashSet<>(user.getAddresses());
        user.getAddresses().clear();
        User createdUser = userRepository.save(user);

        for (Address address: oldAddresses){
            prepareAddress(address);
            Optional<Address> inMemoryAddress = addressRepository.findAll()
                    .stream()
                    .filter(addr -> addr.equals(address))
                    .findFirst();

            if (inMemoryAddress.isPresent()){
                System.out.println("Address exists: "+inMemoryAddress.get());
                createdUser.getAddresses().add(inMemoryAddress.get());
            } else {
                System.out.println("Address does not exists: ");
                Address newAddress = addressRepository.save(address);
                createdUser.getAddresses().add(newAddress);
            }
        }
        return userRepository.save(createdUser);
    }

    private void prepareAddress(Address address){
        address.setCountry(address.getCountry().strip());
        address.setCity(address.getCity().strip());
        address.setStreet(address.getStreet().strip());
        address.setBuildingNum(address.getBuildingNum().strip());
        address.setRoomNum(address.getRoomNum().strip());
    }
}

package edu.baoss.userservice.service;

import edu.baoss.userservice.dto.AddressDto;
import edu.baoss.userservice.dto.UserDto;
import edu.baoss.userservice.exceptions.*;
import edu.baoss.userservice.model.Address;
import edu.baoss.userservice.model.Building;
import edu.baoss.userservice.model.Role;
import edu.baoss.userservice.model.User;
import edu.baoss.userservice.repository.AddressRepository;
import edu.baoss.userservice.repository.BuildingRepository;
import edu.baoss.userservice.repository.UserRepository;
import edu.baoss.userservice.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final BuildingRepository buildingRepository;
    private final ValidationService validationService;
    private final PasswordEncoder encoder;

    public List<UserDto> getAllUsers(){
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "id")).stream()
                .map(UserDto::new)
                .collect(Collectors.toList());
    }

    public UserDto getUserByLogin(String login) {
        return userRepository.getUserByLogin(login)
                .map(UserDto::new)
                .orElseThrow(() -> new UserNotFoundException("User with login '"+login+"' is not found"));
    }

    public UserDto updateMinRefreshDate(String login, Date date) {
        return userRepository.getUserByLogin(login)
                .map(user -> {
                    user.setMinRefreshDate(date);
                    return new UserDto(userRepository.save(user));
                })
                .orElseThrow(() -> new UserNotFoundException("User with login '"+login+"' is not found"));
    }

    public UserDto register(UserDto userDto) {
        Random random = new Random();
        if (userRepository.existsUserByLogin(userDto.getLogin())){
            throw new LoginExistException("Login is already in use");
        }
        if (userRepository.existsUserByEmail(userDto.getEmail())){
            throw new EmailExistException("Email is already in use");
        }
        if (userRepository.existsUserByIdCardNumber(userDto.getIdCardNumber())){
            throw new IdCardNumberExistException("Id card number is already in use");
        }
        if (!validationService.userValidation(userDto, true)){
            throw new ValidationException("Invalid login, password or id card number");
        }
        User user = new User(userDto);
        user.setRole(Role.USER);
        user.setPassword(encoder.encode(userDto.getPassword()));
        if (user.getIdCardNumber() == null) {
            user.setIdCardNumber(encoder.encode(userDto.getIdCardNumber()));
        }
        if (user.getContractNumber() == null || user.getContractNumber() == 0) {
            user.setContractNumber(userRepository.getMaxContractNumber() + 1);
        }
        if (user.getRegDate() == null) {
            user.setRegDate(new Date());
        }
        if (user.getBalance() == 0.0) {
            user.setBalance(CommonUtils.round(50.0 + random.nextDouble()*300, 2));
        }
        user.setActivityStatus(true);
        user.getAddresses().clear();
        User createdUser = userRepository.save(user);

        userDto.getAddresses()
                .forEach(address -> addAddressForUser(createdUser, address, false));
        return new UserDto(userRepository.save(createdUser));
    }

    public UserDto addAddressForUser(User user, AddressDto addressDto, boolean saveUser) {
        if (userRepository.existsById(user.getId())) {
            Building buildingFromDto = new Building(addressDto);
            Optional<Building> buildingFromDb = buildingRepository.findAll().stream()
                    .filter(buildingFromDto::equals)
                    .findFirst();
            if (buildingFromDb.isPresent()) {
                Address addressFromDto = new Address(addressDto, buildingFromDb.get());
                Optional<Address> addressFromDb = addressRepository.findAll()
                        .stream()
                        .filter(addressFromDto::equals)
                        .findFirst();
                if (addressFromDb.isPresent()) {
                    user.getAddresses().add(addressFromDb.get());
                } else {
                    user.getAddresses().add(addressFromDto);
                }
            } else {
                user.getAddresses().add(addressRepository.save(
                        new Address(addressDto, buildingRepository.save(buildingFromDto))));
            }
        } else {
            throw new UserNotFoundException("User is not found");
        }
        return saveUser ? new UserDto(userRepository.save(user)) : new UserDto(user);
    }

    public UserDto deleteAddressForUser(long userId, long addressId) {
        userRepository.deleteAddressForUser(userId, addressId);
        return userRepository.findById(userId)
                .map(UserDto::new)
                .orElseThrow(() -> new UserNotFoundException("User with id '"+userId+"' is not found"));
    }

    public UserDto updateUser(UserDto userDto) {
        if (userRepository.existsById(userDto.getId())) {
            boolean userValid;
            if (!userDto.getPassword().equals(userRepository.getPasswordByLogin(userDto.getLogin()))){
                userValid = validationService.userValidation(userDto, true);
                userDto.setPassword(encoder.encode(userDto.getPassword()));
            } else {
                userValid = validationService.userValidation(userDto, false);
            }
            if (userValid)
                return new UserDto(userRepository.save(new User(userDto)));
            else
                throw new ValidationException("User data is not valid");
        } else {
            throw new UserNotFoundException("User is not found");
        }
    }

}

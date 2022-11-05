package edu.baoss.userservice.dto;

import edu.baoss.userservice.Constants;
import edu.baoss.userservice.model.Gender;
import edu.baoss.userservice.model.Role;
import edu.baoss.userservice.model.User;
import edu.baoss.userservice.utils.CommonUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto implements Serializable {
    private long id;

    private String name;

    private String surname;

    private String login;

    private String password;

    private String email;

    private String phoneNumber;

    private String birthday;

    private String idCardNumber;

    private Integer contractNumber;

    private Gender gender;

    private Role role;

    private boolean activityStatus;

    private String regDate;

    private Date minRefreshDate;

    private double balance;

    private Set<AddressDto> addresses;

    public UserDto(User user) {
        id = user.getId();
        name = user.getName();
        surname = user.getSurname();
        login = user.getLogin();
        password = user.getPassword();
        email = user.getEmail();
        phoneNumber = user.getPhoneNumber();
        birthday = user.getBirthday() == null ? null : Constants.ONLY_DATE_FORMAT.format(user.getBirthday());
        idCardNumber = user.getIdCardNumber();
        contractNumber = user.getContractNumber();
        gender = user.getGender();
        role = user.getRole();
        activityStatus = user.isActivityStatus();
        regDate = user.getRegDate() == null ? null : Constants.DATE_AND_TIME_FORMAT.format(user.getRegDate());
        minRefreshDate = user.getMinRefreshDate();
        balance = CommonUtils.round(user.getBalance(), 2);
        addresses = user.getAddresses().stream()
                .map(AddressDto::new)
                .collect(Collectors.toSet());
    }

}

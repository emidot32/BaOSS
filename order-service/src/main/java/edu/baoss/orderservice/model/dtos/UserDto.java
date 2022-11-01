package edu.baoss.orderservice.model.dtos;


import edu.baoss.orderservice.model.enums.Gender;
import edu.baoss.orderservice.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

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

}


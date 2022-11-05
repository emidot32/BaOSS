package edu.baoss.userservice.model;


import edu.baoss.userservice.Constants;
import edu.baoss.userservice.dto.AddressDto;
import edu.baoss.userservice.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static javax.persistence.CascadeType.*;
import static javax.persistence.CascadeType.DETACH;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    long id;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "surname", nullable = false)
    String surname;

    @Column(name = "login", unique = true)
    String login;

    @Column(name = "password", nullable = false)
    String password;

    @Column(name = "email", unique = true)
    String email;

    @Column(name = "phone_number")
    String phoneNumber;

    @Column(name = "birthday")
    Date birthday;

    @Column(name = "id_card_number")
    String idCardNumber;

    @Column(name = "contract_number")
    Integer contractNumber;

    @Enumerated(EnumType.STRING)
    @Column(name="gender", length = 10)
    Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name="usr_role", length = 30)
    Role role;

    @Column(name = "balance", nullable = false)
    double balance;

    @Column(name = "activity_status")
    boolean activityStatus;

    @Column(name = "reg_date")
    Date regDate;

    @Column(name = "min_refresh_date")
    Date minRefreshDate;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {MERGE, REMOVE, REFRESH, DETACH})
    @JoinTable(name = "user_address",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id"))
    Set<Address> addresses = new HashSet<>();
    
    public User(UserDto userDto) {
        id = userDto.getId();
        name = userDto.getName();
        surname = userDto.getSurname();
        login = userDto.getLogin();
        password = userDto.getPassword();
        email = userDto.getEmail();
        phoneNumber = userDto.getPhoneNumber();
        try {
            birthday = userDto.getBirthday() == null ? null : Constants.ONLY_DATE_FORMAT.parse(userDto.getBirthday());
            regDate = userDto.getRegDate() == null ? null : Constants.DATE_AND_TIME_FORMAT.parse(userDto.getRegDate());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        idCardNumber = userDto.getIdCardNumber();
        contractNumber = userDto.getContractNumber();
        gender = userDto.getGender();
        role = userDto.getRole();
        activityStatus = userDto.isActivityStatus();

        minRefreshDate = userDto.getMinRefreshDate();
        balance = userDto.getBalance();
        addresses = userDto.getAddresses().stream()
                .map(Address::new)
                .collect(Collectors.toSet());
    }


}


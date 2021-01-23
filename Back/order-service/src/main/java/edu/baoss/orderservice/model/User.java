package edu.baoss.orderservice.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="usr")
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

    @Column(name = "id_card_number", unique = true)
    String idCardNumber;

    @Column(name = "contract_number", unique = true)
    int contractNumber;

    @Enumerated(EnumType.STRING)
    @Column(name="gender", length = 7)
    Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name="usr_role", length = 30)
    Role role;

    @Column(name = "activity_status")
    boolean activityStatus;

    @Column(name = "reg_date")
    Date regDate;

    @Column(name = "min_refresh_date")
    Date minRefreshDate;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {
            CascadeType.ALL,
    })
    @JoinTable(name = "user_address",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id"))
    Set<Address> addresses = new HashSet<>();

    @OneToMany(mappedBy = "user")
    Set<BillingAccount> billingAccounts;

    @OneToMany(mappedBy = "user")
    Set<Order> orders;

    @OneToMany(mappedBy = "user")
    Set<Instance> instances;
}


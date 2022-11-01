package edu.baoss.billingservice.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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

    @Column(name = "activity_status")
    boolean activityStatus;

    @Column(name = "balance", nullable = false)
    double balance;

    @Column(name = "reg_date")
    Date regDate;

    @Column(name = "min_refresh_date")
    Date minRefreshDate;

//    @JsonIgnore
//    @OneToMany(mappedBy = "user")
//    Set<BillingAccount> billingAccounts;
}



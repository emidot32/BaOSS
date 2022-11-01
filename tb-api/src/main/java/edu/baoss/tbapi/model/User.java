package edu.baoss.tbapi.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {
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

    private Set<Address> addresses;
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(role);
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return activityStatus;
    }
}


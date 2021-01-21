package edu.baoss.tbapi.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import java.util.Date;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {
    long id;
    String name;
    String surname;
    String login;
    String password;
    String email;
    String phoneNumber;
    Date birthday;
    String idCardNumber;
    int contractNumber;
    Gender gender;
    Role role;
    boolean activityStatus;
    Date regDate;
    Date minRefreshDate;
    Set<Address> addresses;
    
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


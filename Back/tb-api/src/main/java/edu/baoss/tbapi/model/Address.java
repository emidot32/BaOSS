package edu.baoss.tbapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
    long id;
    String country;
    String city;
    String street;
    String buildingNum;
    String roomNum;
    String index;
    Set<User> users;
}

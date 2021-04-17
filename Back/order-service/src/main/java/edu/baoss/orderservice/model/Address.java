package edu.baoss.orderservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    long id;

    @Column(name = "country", nullable = false)
    String country;

    @Column(name = "city", nullable = false)
    String city;

    @Column(name = "street", nullable = false)
    String street;

    @Column(name = "building_num", nullable = false)
    String buildingNum;

    @Column(name = "room_num", nullable = false)
    String roomNum;

    @Column(name = "index")
    String index;

    @JsonIgnore
    @Transient
    @ManyToMany(mappedBy = "addresses")
    Set<User> users;

    public boolean equalsByBuilding(Address address) {
        return  country.equals(address.country) &&
                city.equals(address.city) &&
                street.equals(address.street) &&
                buildingNum.equals(address.buildingNum);
    }
}

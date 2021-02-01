package edu.baoss.userservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return country.equalsIgnoreCase(address.country) &&
                city.equalsIgnoreCase(address.city) &&
                street.equalsIgnoreCase(address.street) &&
                buildingNum.equalsIgnoreCase(address.buildingNum) &&
                roomNum.equalsIgnoreCase(address.roomNum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, city, street, buildingNum, roomNum);
    }

    @Override
    public String toString() {
        List<Long> userIds = null;
        if (users != null)
            userIds = users.stream().map(User::getId).collect(Collectors.toList());
        return "Address{" +
                "id=" + id +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", buildingNum='" + buildingNum + '\'' +
                ", roomNum='" + roomNum + '\'' +
                ", index='" + index + '\'' +
                ", users=" + userIds +
                '}';
    }
}

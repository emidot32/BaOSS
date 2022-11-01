package edu.baoss.orderservice.model.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="buildings")
public class Building implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "building_id")
    long id;

    @Column(name = "country", nullable = false)
    String country;

    @Column(name = "city", nullable = false)
    String city;

    @Column(name = "street", nullable = false)
    String street;

    @Column(name = "building_number", nullable = false)
    String buildingNum;

    @Column(name = "index")
    String index;

    @Column(name = "latitude")
    String latitude;

    @Column(name = "longitude")
    String longitude;

    @OneToMany(mappedBy = "building")
    @JsonIgnore
    Set<Address> addresses;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Building building = (Building) o;
        return id == building.id && Objects.equals(country, building.country) && Objects.equals(city, building.city) && Objects.equals(street, building.street) && Objects.equals(buildingNum, building.buildingNum) && Objects.equals(index, building.index) && Objects.equals(latitude, building.latitude) && Objects.equals(longitude, building.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, country, city, street, buildingNum, index, latitude, longitude);
    }
}

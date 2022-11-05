package edu.baoss.userservice.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.baoss.userservice.dto.AddressDto;
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

    public Building(AddressDto addressDto) {
        id = addressDto.getBuildingId();
        country = addressDto.getCountry().strip();
        city = addressDto.getCity().strip();
        street = addressDto.getStreet().strip();
        buildingNum = addressDto.getBuildingNum().strip();
        index = addressDto.getIndex() == null ? null : addressDto.getIndex().strip();
        latitude = addressDto.getLatitude() == null ? null : addressDto.getLatitude().strip();
        longitude = addressDto.getLongitude() == null ? null : addressDto.getLongitude().strip();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Building building = (Building) o;
        return country.equals(building.country) && city.equals(building.city) && street.equals(building.street) && buildingNum.equals(building.buildingNum)
                && (building.getIndex() == null || index == null || index.equals(building.getIndex()))
                && (building.getLatitude() == null || latitude == null || latitude.equals(building.getLatitude()))
                && (building.getLongitude() == null || longitude == null || longitude.equals(building.getLongitude()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, city, street, buildingNum, index, latitude, longitude);
    }

    @Override
    public String toString() {
        return "Building{" +
                "id=" + id +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", buildingNum='" + buildingNum + '\'' +
                ", index='" + index + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }
}

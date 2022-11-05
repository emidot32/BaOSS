package edu.baoss.userservice.model;

import edu.baoss.userservice.dto.AddressDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import static javax.persistence.CascadeType.*;
import static javax.persistence.CascadeType.DETACH;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="addresses")
public class Address implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    long id;

    @Column(name = "room_number", nullable = false)
    String roomNum;

    @Column(name = "entrance")
    String entrance;

    @ManyToOne(cascade = {REMOVE, REFRESH, DETACH})
    @JoinColumn(name = "building_id", referencedColumnName = "building_id")
    Building building;

    public Address(AddressDto addressDto, Building building) {
        id = addressDto.getAddressId();
        roomNum = addressDto.getRoomNum() == null ? null : addressDto.getRoomNum().strip();
        entrance = addressDto.getEntrance() == null ? null : addressDto.getEntrance().strip();
        this.building = building;
    }

    public Address(AddressDto addressDto) {
        id = addressDto.getAddressId();
        roomNum = addressDto.getRoomNum() == null ? null : addressDto.getRoomNum().strip();
        entrance = addressDto.getEntrance() == null ? null : addressDto.getEntrance().strip();
        building = new Building(addressDto);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return roomNum.equals(address.roomNum)
                && (address.getEntrance() == null || entrance == null || entrance.equals(address.getEntrance()))
                && building.equals(address.building);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNum, entrance, building);
    }
}

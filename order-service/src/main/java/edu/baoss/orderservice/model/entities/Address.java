package edu.baoss.orderservice.model.entities;

import edu.baoss.orderservice.model.dtos.AddressDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
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

    @ManyToOne(cascade = {MERGE, REMOVE, REFRESH, DETACH})
    @JoinColumn(name = "building_id", referencedColumnName = "building_id")
    Building building;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="user_address", joinColumns=@JoinColumn(name="address_id", referencedColumnName = "address_id"))
    @Column(name="user_id")
    List<Long> userIds;

    public Address(AddressDto addressDto) {
        id = addressDto.getAddressId();
        roomNum = addressDto.getRoomNum();
        entrance = addressDto.getEntrance();
        building = new Building(addressDto.getBuildingId(), addressDto.getCountry(), addressDto.getCity(),
                addressDto.getStreet(), addressDto.getBuildingNum(), addressDto.getIndex(),
                addressDto.getLatitude(), addressDto.getLongitude(), null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return id == address.id && Objects.equals(roomNum, address.roomNum) && Objects.equals(entrance, address.entrance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roomNum, entrance);
    }
}

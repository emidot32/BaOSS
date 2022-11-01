package edu.baoss.orderservice.model.dtos;

import edu.baoss.orderservice.model.entities.Address;
import edu.baoss.orderservice.model.entities.Building;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddressDto {
    private long addressId;
    private long buildingId;
    private String country;
    private String city;
    private String street;
    private String buildingNum;
    private String index;
    private String latitude;
    private String longitude;
    private String roomNum;
    private String entrance;

    public AddressDto(Building building, Address address) {
        this.addressId = address.getId();
        this.country = building.getCountry();
        this.city = building.getCity();
        this.street = building.getStreet();
        this.buildingNum = building.getBuildingNum();
        this.index = building.getIndex();
        this.latitude = building.getLatitude();
        this.longitude = building.getLongitude();
        this.roomNum = address.getRoomNum();
        this.entrance = address.getEntrance();
    }

    public AddressDto(Address address) {
        this.addressId = address.getId();
        this.country = address.getBuilding().getCountry();
        this.city = address.getBuilding().getCity();
        this.street = address.getBuilding().getStreet();
        this.buildingNum = address.getBuilding().getBuildingNum();
        this.index = address.getBuilding().getIndex();
        this.latitude = address.getBuilding().getLatitude();
        this.longitude = address.getBuilding().getLongitude();
        this.roomNum = address.getRoomNum();
        this.entrance = address.getEntrance();
    }
}

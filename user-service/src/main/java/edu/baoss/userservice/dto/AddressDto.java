package edu.baoss.userservice.dto;

import edu.baoss.userservice.model.Address;
import edu.baoss.userservice.model.Building;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    public AddressDto(Address address) {
        this.addressId = address.getId();
        this.buildingId = address.getBuilding().getId();
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

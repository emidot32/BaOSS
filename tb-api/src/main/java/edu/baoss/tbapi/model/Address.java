package edu.baoss.tbapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
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
}

package edu.baoss.resourceservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.HashSet;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "connected_buildings")
public class ConnectedBuilding {
    @Id
    private String id;
    @Field("building_id")
    private long buildingId;
    @Field("connected_addresses")
    @DocumentReference
    private Set<ConnectedAddress> connectedAddresses = new HashSet<>();
    @Field("switch_mac_address")
    private String switchMacAddress;
    @Field("switch_serial_number")
    private String switchSerialNumber;

    public void connectAddress(long addressId, String macAddress) {
        connectedAddresses.add(new ConnectedAddress(addressId, macAddress));
    }

}

package edu.baoss.resourceservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "connected_address")
public class ConnectedAddress {
    @Id
    private String id;
    @Field("address_id")
    private long addressId;
    @Field("mac_address")
    private String macAddress;

    public ConnectedAddress(long addressId, String macAddress) {
        this.addressId = addressId;
        this.macAddress = macAddress;
    }
}

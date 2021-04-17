package edu.baoss.resourceservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConnectedAddress {
    @Field("address_id")
    long addressId;
    @Field("mac_address")
    String macAddress;
}

package edu.baoss.orderservice.model.dtos;

import edu.baoss.orderservice.model.entities.Address;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class InternetProductInstance extends ProductInstance {
    AddressDto address;
    InternetOffer internetOffer;
    String fixedIp;
    int cableLen;
    UserDevice device;
}

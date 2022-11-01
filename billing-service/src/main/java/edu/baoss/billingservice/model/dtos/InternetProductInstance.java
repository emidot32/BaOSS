package edu.baoss.billingservice.model.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class InternetProductInstance extends ProductInstance {
    InternetOffer internetOffer;
    String fixedIp;
    int cableLen;

    @Override
    public Offer getOffer() {
        return internetOffer;
    }
}

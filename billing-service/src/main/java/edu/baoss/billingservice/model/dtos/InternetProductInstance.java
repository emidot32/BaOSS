package edu.baoss.billingservice.model.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class InternetProductInstance extends ProductInstance {
    InternetOffer internetOffer;
    String fixedIp;
    int cableLen;

    @Override
    public Offer getOffer() {
        return internetOffer;
    }
}

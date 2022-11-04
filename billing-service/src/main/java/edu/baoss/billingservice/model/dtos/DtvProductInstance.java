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
public class DtvProductInstance extends ProductInstance {
    long internetProductId;
    DtvOffer dtvOffer;

    @Override
    public Offer getOffer() {
        return dtvOffer;
    }
}

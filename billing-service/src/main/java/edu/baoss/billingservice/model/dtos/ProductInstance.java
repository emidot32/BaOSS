package edu.baoss.billingservice.model.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class ProductInstance {
    long id;
    long instanceId;
    long userId;
    String activatedDateStr;
    String disconnectedDateStr;
    String expiredDate;
    String product;

    public Offer getOffer() {
        return null;
    }
}

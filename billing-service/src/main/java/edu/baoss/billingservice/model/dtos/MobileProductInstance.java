package edu.baoss.billingservice.model.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MobileProductInstance extends ProductInstance {
    Tariff tariff;
    int balance;
    boolean support5g;

    @Override
    public Offer getOffer() {
        return tariff;
    }
}

package edu.baoss.orderservice.model.dtos;

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
    PhoneNumber phoneNumber;
    int balance;
    boolean support5g;

}

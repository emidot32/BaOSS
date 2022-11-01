package edu.baoss.billingservice.model.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class Offer {
    String id;
    double startingPrice;
    double discountedPrice;
    int discount;
    String discountEndDate;
    String priceEnding;
    String currency;
    Integer rentTime;


}

package edu.baoss.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtvOffer {
    int channelNumber;
    String id;
    double startingPrice;
    double discountedPrice;
    int discount;
    String discountEndDate;
    String priceEnding;
}

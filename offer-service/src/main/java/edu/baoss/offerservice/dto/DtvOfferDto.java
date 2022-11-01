package edu.baoss.offerservice.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DtvOfferDto extends OfferDto {
    int channelNumber;
    String id;
    double startingPrice;
    double discountedPrice;
    int discount;
    String discountEndDate;
    String priceEnding;
}

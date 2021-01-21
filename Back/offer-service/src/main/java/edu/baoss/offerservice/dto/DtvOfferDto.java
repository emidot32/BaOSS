package edu.baoss.offerservice.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DtvOfferDto implements IOfferDto {
    Integer channelNumber;
    String id;
    Double startingPrice;
    Double discountedPrice;
    Integer discount;
    String discountEndDate;
    String priceEnding;
}

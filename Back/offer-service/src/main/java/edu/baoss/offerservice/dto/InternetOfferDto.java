package edu.baoss.offerservice.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InternetOfferDto implements IOfferDto {
    Integer speed;
    String id;
    Double startingPrice;
    Double discountedPrice;
    Integer discount;
    String discountEndDate;
    String priceEnding;
}

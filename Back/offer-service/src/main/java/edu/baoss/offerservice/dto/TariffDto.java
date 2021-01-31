package edu.baoss.offerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TariffDto implements IOfferDto {
    String id;
    double startingPrice;
    double discountedPrice;
    int discount;
    String discountEndDate;
    String priceEnding;
    String tariff_name;
    int internetGBs;
    int freeMinutes;
    int freeSms;
    double roamingPerMinuteCallPrice;
    double roamingPerMinuteInternetPrice;
    double oneSmsPrice;
    double minuteOfCallPrice;
}

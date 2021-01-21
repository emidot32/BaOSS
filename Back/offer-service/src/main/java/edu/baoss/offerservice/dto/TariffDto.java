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
    Double startingPrice;
    Double discountedPrice;
    Integer discount;
    String discountEndDate;
    String priceEnding;
    String tariff_name;
    Integer internet_GBs;
    Integer free_minutes;
    Integer free_sms;
    Double roaming_per_minute_call_price;
    Double roaming_per_minute_internet_price;
    Double one_sms_price;
    Double minute_of_call_price;
}

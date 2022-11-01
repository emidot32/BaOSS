package edu.baoss.offerservice.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TariffDto extends OfferDto {
    String tariffName;
    int internetGBs;
    int freeMinutes;
    int freeSms;
    double roamingPerMinuteCallPrice;
    double roamingPerMinuteInternetPrice;
    double oneSmsPrice;
    double minuteOfCallPrice;
}

package edu.baoss.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tariff {
    String id;
    double startingPrice;
    double discountedPrice;
    int discount;
    String discountEndDate;
    String priceEnding;
    String tariffName;
    int internetGBs;
    int freeMinutes;
    int freeSms;
    double roamingPerMinuteCallPrice;
    double roamingPerMinuteInternetPrice;
    double oneSmsPrice;
    double minuteOfCallPrice;
}

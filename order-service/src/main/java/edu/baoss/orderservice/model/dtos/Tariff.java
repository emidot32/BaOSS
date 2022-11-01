package edu.baoss.orderservice.model.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Tariff extends Offer implements Serializable {
    String tariffName;
    int internetGBs;
    int freeMinutes;
    int freeSms;
    double roamingPerMinuteCallPrice;
    double roamingPerMinuteInternetPrice;
    double oneSmsPrice;
    double minuteOfCallPrice;
}

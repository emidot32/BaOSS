package edu.baoss.offerservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "tariffs")
public class Tariff implements IOffer{
    @Id
    private String id;
    @Field("tariff_name")
    private String tariff_name;
    private double rent;
    @Field("rent_time")
    private int rentTime;
    @Field("internet_GBs")
    private int internetGBs;
    @Field("free_minutes")
    private int freeMinutes;
    @Field("free_sms")
    private int freeSms;
    @Field("roaming_per_minute_call_price")
    private double roamingPerMinuteCallPrice;
    @Field("roaming_per_minute_internet_price")
    private double roamingPerMinuteInternetPrice;
    @Field("one_sms_price")
    private double oneSmsPrice;
    @Field("minute_of_call_price")
    private double minuteOfCallPrice;
    private String currency;
}

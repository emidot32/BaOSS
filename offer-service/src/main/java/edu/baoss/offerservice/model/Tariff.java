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
public class Tariff implements Offer {
    @Id
    private String id;
    @Field("tariff_name")
    private String tariffName;
    private Double rent;
    @Field("rent_time")
    private Integer rentTime;
    @Field("internet_GBs")
    private Integer internetGBs;
    @Field("free_minutes")
    private Integer freeMinutes;
    @Field("free_sms")
    private Integer freeSms;
    @Field("roaming_per_minute_call_price")
    private Double roamingPerMinuteCallPrice;
    @Field("roaming_per_minute_internet_price")
    private Double roamingPerMinuteInternetPrice;
    @Field("one_sms_price")
    private Double oneSmsPrice;
    @Field("minute_of_call_price")
    private Double minuteOfCallPrice;
    private String currency;
}

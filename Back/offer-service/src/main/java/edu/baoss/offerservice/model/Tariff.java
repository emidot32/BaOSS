package edu.baoss.offerservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
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

    private String tariff_name;

    private int rent;

    private int rent_time;

    private int internet_GBs;

    private int free_minutes;

    private int free_sms;

    private double roaming_per_minute_call_price;

    private double roaming_per_minute_internet_price;

    private double one_sms_price;

    private double minute_of_call_price;

    private String currency;
}

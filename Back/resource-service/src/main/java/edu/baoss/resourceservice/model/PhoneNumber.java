package edu.baoss.resourceservice.model;

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
@Document(collection = "phone_numbers")
public class PhoneNumber {
    @Id
    private String id;
    @Field("phone_number")
    private String phoneNumber;
    private boolean used;
    @Field("sim_card_number")
    private String simCardNumber;
    private double price;
    @Field("country_code")
    private String countryCode;
    @Field("pin_code")
    private String pinCode;
    @Field("puk_code")
    private String pukCode;

}

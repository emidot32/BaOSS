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
@Document(collection = "dtv_price_list")
public class DtvOffer implements Offer {
    @Id
    private String id;
    private Double rent;
    @Field("rent_time")
    private Integer rentTime;
    @Field("channel_number")
    private Integer channelNumber;
    private String currency;


}

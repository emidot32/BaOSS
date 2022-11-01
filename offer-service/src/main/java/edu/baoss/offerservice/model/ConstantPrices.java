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
@Document(collection = "constant_prices")
public class ConstantPrices {
    @Id
    private String id;
    @Field("fixed_ip_price")
    private Double fixedIpPrice;
    @Field("ipv6_support_price")
    private Double ipv6SupportPrice;
    @Field("installation_price_for_internet_product")
    private Double installationPriceForInternetProduct;
    @Field("installation_price_for_dtv_product")
    private Double installationPriceForDtvProduct;
    @Field("delivery_price")
    private Double deliveryPrice;
    @Field("support_of_5g_price")
    private Double supportOf5gPrice;
    @Field("cable_one_meter_price")
    private Double cableOneMeterPrice;
    private String currency;
}

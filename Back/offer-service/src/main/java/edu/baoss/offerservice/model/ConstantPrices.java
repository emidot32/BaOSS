package edu.baoss.offerservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
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
    private double fixed_ip_price;
    @Field("ipv6_support_price")
    private double ipv6_support_price;
    @Field("installation_price_for_internet_product")
    private double installationPriceForInternetProduct;
    @Field("installation_price_for_dtv_product")
    private double installationPriceForDtvProduct;
    @Field("delivery_price")
    private double deliveryPrice;
    @Field("support_of_5g_price")
    private double supportOf5gPrice;
    @Field("cable_one_meter_price")
    private double cableOneMeterPrice;
}

package edu.baoss.offerservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
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

    private int fixed_ip_price;

    private int ipv6_support_price;

    private int installation_price_for_internet_product;

    private int installation_price_for_dtv_product;

    private int delivery_price;

    private int support_of_5g_price;
}

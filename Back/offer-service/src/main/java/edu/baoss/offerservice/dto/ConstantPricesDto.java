package edu.baoss.offerservice.dto;

import edu.baoss.offerservice.model.IOffer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConstantPricesDto implements IOfferDto {
    String id;
    double[] fixed_ip_prices;
    double[] ipv6_support_prices;
    double[] installation_prices_for_internet_product;
    double[] installation_prices_for_dtv_product;
    double[] delivery_prices;
    double[] support_of_5g_prices;
    Integer discount;
    String discountEndDate;
}

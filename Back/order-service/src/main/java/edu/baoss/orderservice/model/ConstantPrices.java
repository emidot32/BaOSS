package edu.baoss.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConstantPrices implements Serializable {
    String id;
    double[] fixedIpPrices;
    double[] ipv6SupportPrices;
    double[] installationPricesForInternetProduct;
    double[] installationPricesForDtvProduct;
    double[] deliveryPrices;
    double[] supportOf5gPrices;
    double[] cableOneMeterPrice;
    int discount;
    String discountEndDate;
}

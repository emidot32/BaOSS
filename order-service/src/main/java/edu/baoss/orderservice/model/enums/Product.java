package edu.baoss.orderservice.model.enums;

import edu.baoss.orderservice.Constants;
import lombok.Getter;

@Getter
public enum Product {
    MOBILE(Constants.MOBILE_PRODUCT_STR),
    INTERNET(Constants.INTERNET_PRODUCT_STR),
    DTV(Constants.DTV_PRODUCT_STR);

    private String name;

    Product(String name) {
        this.name = name;
    }
}

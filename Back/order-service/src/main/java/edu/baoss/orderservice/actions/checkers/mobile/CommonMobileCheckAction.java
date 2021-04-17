package edu.baoss.orderservice.actions.checkers.mobile;

import edu.baoss.orderservice.actions.checkers.CheckAction;
import edu.baoss.orderservice.dtos.OrderValue;
import edu.baoss.orderservice.exceptions.*;

import java.util.Arrays;

import static edu.baoss.orderservice.services.Constants.MOBILE_PRODUCT_STR;

public class CommonMobileCheckAction implements CheckAction {
    @Override
    public void check(OrderValue orderValue) {
        if (Arrays.asList(orderValue.getSelectedProducts()).contains(MOBILE_PRODUCT_STR)) {
            if (orderValue.getSelectedTariff() == null)
                throw new NoMobileTariffInOrderValueException("Mobile tariff is absent but Mobile Product is selected!");
            if (orderValue.getSelectedPhoneNumber() == null)
                throw new NoPhoneNumberInOrderValueException("Phone number is absent but Mobile Product is selected!");
            if (orderValue.isDeliveryAndActivationMobile() && orderValue.getSelectedAddress() == null)
                throw new NoAddressInOrderValueException("Order configuration needs address for delivery or installation!");
        }
    }
}


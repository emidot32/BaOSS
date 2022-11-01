package edu.baoss.orderservice.actions.checking.mobile;

import edu.baoss.orderservice.actions.checking.FeasibilityCheckAction;
import edu.baoss.orderservice.model.ActionContext;
import edu.baoss.orderservice.model.dtos.OrderValue;
import edu.baoss.orderservice.exceptions.*;

import java.util.Arrays;

import static edu.baoss.orderservice.Constants.MOBILE_PRODUCT_STR;

public class CommonMobileCheckAction extends FeasibilityCheckAction {
    @Override
    public void check(ActionContext context) {
        if (Arrays.asList(context.getOrderValue().getSelectedProducts()).contains(MOBILE_PRODUCT_STR)) {
            if (context.getOrderValue().getSelectedTariff() == null)
                throw new NoMobileTariffInOrderValueException("Mobile tariff is absent but Mobile Product is selected!");
            if (context.getOrderValue().getSelectedPhoneNumber() == null)
                throw new NoPhoneNumberInOrderValueException("Phone number is absent but Mobile Product is selected!");
            if (context.getOrderValue().isDeliveryAndActivationMobile()) {
                if (context.getOrderValue().getSelectedAddress() == null)
                    throw new NoAddressInOrderValueException("Order configuration needs address for delivery or installation!");
                if (context.getOrderValue().getDeliveryTime() == null) {
                    throw new DeliveryNotSpecifiedException("Delivery time is not specified!");
                }
            }

        }
    }
}


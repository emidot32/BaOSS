package edu.baoss.orderservice.actions.checking.internet;

import edu.baoss.orderservice.actions.checking.FeasibilityCheckAction;
import edu.baoss.orderservice.model.ActionContext;
import edu.baoss.orderservice.model.dtos.OrderValue;
import edu.baoss.orderservice.exceptions.DeliveryNotSpecifiedException;
import edu.baoss.orderservice.exceptions.NoAddressInOrderValueException;
import edu.baoss.orderservice.exceptions.WrongCableLengthException;
import edu.baoss.orderservice.exceptions.NoInternetSpeedInOrderValueException;

import java.util.Arrays;

import static edu.baoss.orderservice.Constants.INTERNET_PRODUCT_STR;

public class CommonInternetCheckAction extends FeasibilityCheckAction {
    @Override
    public void check(ActionContext context) {
        if (Arrays.asList(context.getOrderValue().getSelectedProducts()).contains(INTERNET_PRODUCT_STR)) {
            if (context.getOrderValue().getSelectedSpeed() == null)
                throw new NoInternetSpeedInOrderValueException("Internet speed is absent but internet product is presented!");
            if (context.getOrderValue().getCableLength() <= 0 || context.getOrderValue().getCableLength() > 30)
                throw new WrongCableLengthException("Cable length must be between 0 and 30!");
            if (context.getOrderValue().getSelectedAddress() == null)
                throw new NoAddressInOrderValueException("Order configuration needs address for delivery or installation!");
            if (context.getOrderValue().getDeliveryTime() == null) {
                throw new DeliveryNotSpecifiedException("Delivery time is not specified!");
            }
        }
    }

}

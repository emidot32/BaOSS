package edu.baoss.orderservice.actions.checkers.internet;

import edu.baoss.orderservice.actions.checkers.CheckAction;
import edu.baoss.orderservice.dtos.OrderValue;
import edu.baoss.orderservice.exceptions.NoAddressInOrderValueException;
import edu.baoss.orderservice.exceptions.WrongCableLengthException;
import edu.baoss.orderservice.exceptions.NoInternetSpeedInOrderValueException;

import java.util.Arrays;

import static edu.baoss.orderservice.services.Constants.INTERNET_PRODUCT_STR;

public class CommonInternetCheckAction implements CheckAction {
    @Override
    public void check(OrderValue orderValue) {
        if (Arrays.asList(orderValue.getSelectedProducts()).contains(INTERNET_PRODUCT_STR)) {
            if (orderValue.getSelectedSpeed() == null)
                throw new NoInternetSpeedInOrderValueException("Internet speed is absent but internet product is presented!");
            if (orderValue.getCableLength() <= 0 || orderValue.getCableLength() > 30)
                throw new WrongCableLengthException("Cable length must be between 0 and 30!");
            if (orderValue.getSelectedAddress() == null)
                throw new NoAddressInOrderValueException("Order configuration needs address for delivery or installation!");
        }
    }

}

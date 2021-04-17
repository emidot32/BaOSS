package edu.baoss.orderservice.actions.checkers.dtv;

import edu.baoss.orderservice.actions.checkers.CheckAction;
import edu.baoss.orderservice.dtos.OrderValue;
import edu.baoss.orderservice.exceptions.DtvProductExistsWithoutInternetProductException;
import edu.baoss.orderservice.exceptions.NoChannelNumbersInOrderValueException;

import java.util.Arrays;
import java.util.List;

import static edu.baoss.orderservice.services.Constants.DTV_PRODUCT_STR;
import static edu.baoss.orderservice.services.Constants.INTERNET_PRODUCT_STR;

public class CommonDtvCheckAction implements CheckAction {
    @Override
    public void check(OrderValue orderValue) {
        List<String> productList = Arrays.asList(orderValue.getSelectedProducts());
        if (productList.contains(DTV_PRODUCT_STR)) {
            if (!productList.contains(INTERNET_PRODUCT_STR))
                throw new DtvProductExistsWithoutInternetProductException("DTV Product is selected but Internet Product is not selected!");
            if (orderValue.getSelectedChannelNumber() == null)
                throw new NoChannelNumbersInOrderValueException("Channel numbers is absent but DTV Product is selected!");
        }
    }
}

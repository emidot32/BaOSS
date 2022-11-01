package edu.baoss.orderservice.actions.checking.dtv;

import edu.baoss.orderservice.actions.checking.FeasibilityCheckAction;
import edu.baoss.orderservice.model.ActionContext;
import edu.baoss.orderservice.model.dtos.OrderValue;
import edu.baoss.orderservice.exceptions.DtvProductExistsWithoutInternetProductException;
import edu.baoss.orderservice.exceptions.NoChannelNumbersInOrderValueException;

import java.util.Arrays;
import java.util.List;

import static edu.baoss.orderservice.Constants.DTV_PRODUCT_STR;
import static edu.baoss.orderservice.Constants.INTERNET_PRODUCT_STR;

public class CommonDtvCheckAction extends FeasibilityCheckAction {
    @Override
    public void check(ActionContext context) {
        List<String> productList = Arrays.asList(context.getOrderValue().getSelectedProducts());
        if (productList.contains(DTV_PRODUCT_STR)) {
            if (!productList.contains(INTERNET_PRODUCT_STR))
                throw new DtvProductExistsWithoutInternetProductException("DTV Product is selected but Internet Product is not selected!");
            if (context.getOrderValue().getSelectedChannelNumber() == null)
                throw new NoChannelNumbersInOrderValueException("Channel numbers is absent but DTV Product is selected!");
        }
    }
}

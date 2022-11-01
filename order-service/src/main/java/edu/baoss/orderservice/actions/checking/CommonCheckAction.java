package edu.baoss.orderservice.actions.checking;

import edu.baoss.orderservice.exceptions.DeliveryNotSpecifiedException;
import edu.baoss.orderservice.exceptions.NoBillingAccountInOrderValueException;
import edu.baoss.orderservice.model.ActionContext;

public class CommonCheckAction extends FeasibilityCheckAction {
    @Override
    public void check(ActionContext context) {
        String deliveryDateStr = context.getOrderValue().getDeliveryDateStr();
        if (deliveryDateStr == null || deliveryDateStr.isEmpty())
            throw new DeliveryNotSpecifiedException("Delivery date is not specified!");
    }
}

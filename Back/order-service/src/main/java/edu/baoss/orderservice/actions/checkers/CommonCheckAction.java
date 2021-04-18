package edu.baoss.orderservice.actions.checkers;

import edu.baoss.orderservice.dtos.OrderValue;
import edu.baoss.orderservice.exceptions.DeliveryNotSpecifiedException;
import edu.baoss.orderservice.exceptions.NoBillingAccountInOrderValueException;

public class CommonCheckAction implements CheckAction{
    @Override
    public void check(OrderValue orderValue) {
        if (orderValue.getSelectedAccount() == null)
            throw new NoBillingAccountInOrderValueException("Billing Account is not specified!");
        String deliveryDateStr = orderValue.getDeliveryDateStr();
        if (deliveryDateStr == null || deliveryDateStr.isEmpty())
            throw new DeliveryNotSpecifiedException("Delivery date is not specified!");
    }
}

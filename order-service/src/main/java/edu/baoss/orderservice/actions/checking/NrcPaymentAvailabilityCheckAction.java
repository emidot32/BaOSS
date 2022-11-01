package edu.baoss.orderservice.actions.checking;

import edu.baoss.orderservice.feign.BillingServiceFeignProxy;
import edu.baoss.orderservice.model.ActionContext;
import edu.baoss.orderservice.model.dtos.OrderValue;
import edu.baoss.orderservice.exceptions.NotAvailableNrcPaymentForBA;

public class NrcPaymentAvailabilityCheckAction extends FeasibilityCheckAction {

    @Override
    public void check(ActionContext context) {
        if (!context.getApplicationContext().getBean(BillingServiceFeignProxy.class)
                .checkNrcPayment(context.getOrderValue().getUserId(), context.getOrderValue().getTotalNRC()))
            throw new NotAvailableNrcPaymentForBA("NRC payment is not available!");
    }
}

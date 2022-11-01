package edu.baoss.orderservice.actions.provisioning.common;

import edu.baoss.orderservice.actions.provisioning.ProvisioningAction;
import edu.baoss.orderservice.model.FulfilmentContext;
import edu.baoss.orderservice.feign.BillingServiceFeignProxy;

public class GetPaymentAction extends ProvisioningAction {
    BillingServiceFeignProxy billingServiceFeignProxy;

    public GetPaymentAction(FulfilmentContext context) {
        super(context);
        this.billingServiceFeignProxy = context.getApplicationContext().getBean(BillingServiceFeignProxy.class);
    }

    @Override
    protected void performAction() {
        billingServiceFeignProxy.doNrcPayment(orderValue.getOrder().getUserId(),
                orderValue.getOrder().getTotalNRC());
    }

    @Override
    public boolean instantiationCondition() {
        return orderValue.getTotalNRC() > 0;
    }
}

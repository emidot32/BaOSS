package edu.baoss.orderservice.actions.provisioning.mobile;

import edu.baoss.orderservice.actions.provisioning.ProvisioningAction;
import edu.baoss.orderservice.feign.ResourceServiceFeignProxy;
import edu.baoss.orderservice.model.FulfilmentContext;

public class WaitingDeliverymanConfirmationAction extends ProvisioningAction {
    ResourceServiceFeignProxy resourceServiceFeignProxy;

    public WaitingDeliverymanConfirmationAction(FulfilmentContext context) {
        super(context);
        this.resourceServiceFeignProxy = context.getApplicationContext().getBean(ResourceServiceFeignProxy.class);
    }

    @Override
    protected void performAction() {

    }

    @Override
    public boolean instantiationCondition() {
        return orderValue.getDeliveryTime() != null
                && orderValue.getSelectedSpeed() == null;
    }
}

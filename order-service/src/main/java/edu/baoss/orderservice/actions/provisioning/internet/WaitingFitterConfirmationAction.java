package edu.baoss.orderservice.actions.provisioning.internet;

import edu.baoss.orderservice.actions.provisioning.ProvisioningAction;
import edu.baoss.orderservice.feign.ResourceServiceFeignProxy;
import edu.baoss.orderservice.model.FulfilmentContext;

public class WaitingFitterConfirmationAction extends ProvisioningAction {
    ResourceServiceFeignProxy resourceServiceFeignProxy;

    public WaitingFitterConfirmationAction(FulfilmentContext context) {
        super(context);
        this.resourceServiceFeignProxy = context.getApplicationContext().getBean(ResourceServiceFeignProxy.class);
    }

    @Override
    protected void performAction() {
        resourceServiceFeignProxy.connectUser(orderValue.getSelectedAddress().getBuildingId(),
                orderValue.getSelectedAddress().getAddressId(), orderValue.getMacAddress());
    }

    @Override
    public boolean instantiationCondition() {
        return orderValue.getDeliveryTime() != null
                && orderValue.getSelectedSpeed() != null
                && orderValue.getSelectedDevice() != null;
    }
}

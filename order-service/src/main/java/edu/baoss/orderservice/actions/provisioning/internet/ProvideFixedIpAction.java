package edu.baoss.orderservice.actions.provisioning.internet;

import edu.baoss.orderservice.actions.provisioning.ProvisioningAction;
import edu.baoss.orderservice.feign.ResourceServiceFeignProxy;
import edu.baoss.orderservice.model.FulfilmentContext;
import edu.baoss.orderservice.services.InstanceService;

public class ProvideFixedIpAction extends ProvisioningAction {
    ResourceServiceFeignProxy resourceServiceFeignProxy;
    InstanceService instanceService;

    public ProvideFixedIpAction(FulfilmentContext context) {
        super(context);
        this.resourceServiceFeignProxy = context.getApplicationContext().getBean(ResourceServiceFeignProxy.class);
        this.instanceService = context.getApplicationContext().getBean(InstanceService.class);
    }

    @Override
    protected void performAction() {
        String fixedIp = resourceServiceFeignProxy.getFixedIP();
        instanceService.setFixedIpToInternetProduct(fixedIp, orderValue);
    }

    @Override
    public boolean instantiationCondition() {
        return orderValue.isFixedIpSupport();
    }
}

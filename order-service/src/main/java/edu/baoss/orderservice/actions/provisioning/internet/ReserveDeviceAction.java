package edu.baoss.orderservice.actions.provisioning.internet;

import edu.baoss.orderservice.actions.provisioning.ProvisioningAction;
import edu.baoss.orderservice.feign.ResourceServiceFeignProxy;
import edu.baoss.orderservice.model.FulfilmentContext;
import edu.baoss.orderservice.model.dtos.UserDevice;
import edu.baoss.orderservice.services.InstanceService;

public class ReserveDeviceAction extends ProvisioningAction {
    ResourceServiceFeignProxy resourceServiceFeignProxy;
    InstanceService instanceService;

    public ReserveDeviceAction(FulfilmentContext context) {
        super(context);
        this.resourceServiceFeignProxy = context.getApplicationContext().getBean(ResourceServiceFeignProxy.class);
        this.instanceService = context.getApplicationContext().getBean(InstanceService.class);
    }

    @Override
    protected void performAction() {
        System.out.println(orderValue.getSelectedDevice().getName());
        UserDevice userDevice = resourceServiceFeignProxy.reserveDevice(orderValue.getSelectedDevice().getId());
        orderValue.setSelectedDevice(userDevice);
        instanceService.setDeviceIdToInternetProduct(orderValue);
    }

    @Override
    public boolean instantiationCondition() {
        return orderValue.getSelectedDevice() != null;
    }
}

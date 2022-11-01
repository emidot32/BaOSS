package edu.baoss.orderservice.actions.provisioning.mobile;

import edu.baoss.orderservice.actions.provisioning.ProvisioningAction;
import edu.baoss.orderservice.model.FulfilmentContext;
import edu.baoss.orderservice.model.dtos.OrderValue;
import edu.baoss.orderservice.model.dtos.PhoneNumber;
import edu.baoss.orderservice.feign.ResourceServiceFeignProxy;
import edu.baoss.orderservice.model.entities.Task;
import edu.baoss.orderservice.model.enums.TaskStatus;
import edu.baoss.orderservice.model.enums.TaskType;
import edu.baoss.orderservice.repositories.TaskRepository;

import static edu.baoss.orderservice.Constants.*;

public class ReserveSimCardAction extends ProvisioningAction {
    ResourceServiceFeignProxy resourceServiceFeignProxy;

    public ReserveSimCardAction(FulfilmentContext fulfilmentContext) {
        super(fulfilmentContext);
        this.resourceServiceFeignProxy = fulfilmentContext.getApplicationContext().getBean(ResourceServiceFeignProxy.class);
    }

    @Override
    protected void performAction() {
        PhoneNumber phoneNumber = resourceServiceFeignProxy.reserveSimCard(orderValue.getSelectedPhoneNumber());
        orderValue.setSelectedPhoneNumber(phoneNumber);
    }

    @Override
    public boolean instantiationCondition() {
        return orderValue.getSelectedPhoneNumber() != null;
    }
}

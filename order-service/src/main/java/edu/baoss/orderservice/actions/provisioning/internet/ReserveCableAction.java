package edu.baoss.orderservice.actions.provisioning.internet;

import edu.baoss.orderservice.actions.provisioning.ProvisioningAction;
import edu.baoss.orderservice.model.FulfilmentContext;
import edu.baoss.orderservice.model.dtos.OrderValue;
import edu.baoss.orderservice.feign.ResourceServiceFeignProxy;
import edu.baoss.orderservice.model.entities.Task;
import edu.baoss.orderservice.model.enums.TaskStatus;
import edu.baoss.orderservice.model.enums.TaskType;
import edu.baoss.orderservice.repositories.TaskRepository;

import static edu.baoss.orderservice.Constants.RESERVE_CABLE_TASK_DESCRIPTION;
import static edu.baoss.orderservice.Constants.RESERVE_CABLE_TASK_NAME;

public class ReserveCableAction extends ProvisioningAction {
    ResourceServiceFeignProxy resourceServiceFeignProxy;

    public ReserveCableAction(FulfilmentContext context) {
        super(context);
        this.resourceServiceFeignProxy = context.getApplicationContext().getBean(ResourceServiceFeignProxy.class);
    }

    @Override
    protected void performAction() {
        resourceServiceFeignProxy.reserveCable(orderValue.getCableLength());
    }

    @Override
    public boolean instantiationCondition() {
        return orderValue.getCableLength() > 0;
    }
}

package edu.baoss.orderservice.actions.provisioning.common;

import edu.baoss.orderservice.actions.provisioning.ProvisioningAction;
import edu.baoss.orderservice.model.FulfilmentContext;
import edu.baoss.orderservice.model.dtos.OrderValue;
import edu.baoss.orderservice.model.entities.Task;
import edu.baoss.orderservice.model.enums.TaskStatus;
import edu.baoss.orderservice.model.enums.TaskType;
import edu.baoss.orderservice.repositories.TaskRepository;

import static edu.baoss.orderservice.Constants.*;

public class NotifyFitterOrDeliverymanAction extends ProvisioningAction {

    public NotifyFitterOrDeliverymanAction(FulfilmentContext fulfilmentContext) {
        super(fulfilmentContext);
    }

    @Override
    protected void performAction() {

    }

    @Override
    public boolean instantiationCondition() {
        return orderValue.getDeliveryTime() != null;
    }

}

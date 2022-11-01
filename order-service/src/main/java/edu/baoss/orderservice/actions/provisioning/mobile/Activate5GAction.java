package edu.baoss.orderservice.actions.provisioning.mobile;

import edu.baoss.orderservice.actions.provisioning.ProvisioningAction;
import edu.baoss.orderservice.model.FulfilmentContext;
import edu.baoss.orderservice.model.dtos.OrderValue;
import edu.baoss.orderservice.model.entities.Task;
import edu.baoss.orderservice.model.enums.TaskStatus;
import edu.baoss.orderservice.model.enums.TaskType;
import edu.baoss.orderservice.repositories.TaskRepository;

import static edu.baoss.orderservice.Constants.*;

public class Activate5GAction extends ProvisioningAction {


    public Activate5GAction(FulfilmentContext fulfilmentContext) {
        super(fulfilmentContext);
    }

    @Override
    protected void performAction() {
        try {
            Thread.sleep(50000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean instantiationCondition() {
        return orderValue.isSupport5g();
    }
}

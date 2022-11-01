package edu.baoss.orderservice.actions.provisioning.dtv;

import edu.baoss.orderservice.actions.provisioning.ProvisioningAction;
import edu.baoss.orderservice.model.FulfilmentContext;
import edu.baoss.orderservice.model.dtos.OrderValue;
import edu.baoss.orderservice.model.entities.Task;
import edu.baoss.orderservice.model.enums.TaskStatus;
import edu.baoss.orderservice.model.enums.TaskType;
import edu.baoss.orderservice.repositories.TaskRepository;

import static edu.baoss.orderservice.Constants.*;

public class ActivateDtvChannelsAction extends ProvisioningAction {

    public ActivateDtvChannelsAction(FulfilmentContext fulfilmentContext) {
        super(fulfilmentContext);
    }

    @Override
    protected void performAction() {

    }

    @Override
    public boolean instantiationCondition() {
        return orderValue.getSelectedChannelNumber() != null;
    }
}

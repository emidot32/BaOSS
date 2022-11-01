package edu.baoss.orderservice.actions.provisioning.internet;

import edu.baoss.orderservice.actions.provisioning.ProvisioningAction;
import edu.baoss.orderservice.model.FulfilmentContext;
import edu.baoss.orderservice.model.dtos.OrderValue;
import edu.baoss.orderservice.model.entities.Task;
import edu.baoss.orderservice.model.enums.TaskStatus;
import edu.baoss.orderservice.model.enums.TaskType;
import edu.baoss.orderservice.repositories.TaskRepository;

import javax.naming.ldap.SortControl;

import static edu.baoss.orderservice.Constants.*;

public class ConnectToNetworkAction extends ProvisioningAction {

    public ConnectToNetworkAction(FulfilmentContext context) {
        super(context);
    }

    @Override
    protected void performAction() {

    }

    @Override
    public boolean instantiationCondition() {
        return orderValue.getSelectedSpeed() != null;
    }
}

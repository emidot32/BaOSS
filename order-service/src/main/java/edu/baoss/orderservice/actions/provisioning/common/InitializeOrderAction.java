package edu.baoss.orderservice.actions.provisioning.common;

import edu.baoss.orderservice.actions.provisioning.ProvisioningAction;
import edu.baoss.orderservice.model.FulfilmentContext;


public class InitializeOrderAction extends ProvisioningAction {


    public InitializeOrderAction(FulfilmentContext fulfilmentContext) {
        super(fulfilmentContext);
    }

    @Override
    protected void performAction() {

    }

    @Override
    public boolean instantiationCondition() {
        return true;
    }
}

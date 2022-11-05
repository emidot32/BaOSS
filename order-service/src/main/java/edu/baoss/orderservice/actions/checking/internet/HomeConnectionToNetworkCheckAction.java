package edu.baoss.orderservice.actions.checking.internet;

import edu.baoss.orderservice.actions.checking.FeasibilityCheckAction;
import edu.baoss.orderservice.exceptions.HomeIsNotConnectedToNetworkException;
import edu.baoss.orderservice.feign.ResourceServiceFeignProxy;
import edu.baoss.orderservice.model.ActionContext;

public class HomeConnectionToNetworkCheckAction extends FeasibilityCheckAction {

    @Override
    public void check(ActionContext context) {
        System.out.println(context.getOrderValue().getSelectedAddress().getBuildingId());
        if (!context.getApplicationContext().getBean(ResourceServiceFeignProxy.class)
                .isBuildingConnectedToNetwork(context.getOrderValue().getSelectedAddress().getBuildingId()))
            throw new HomeIsNotConnectedToNetworkException("The building is not connected to the network");
    }
}

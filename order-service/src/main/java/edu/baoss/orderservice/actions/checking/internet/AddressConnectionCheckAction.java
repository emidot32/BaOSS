package edu.baoss.orderservice.actions.checking.internet;

import edu.baoss.orderservice.actions.checking.FeasibilityCheckAction;
import edu.baoss.orderservice.model.ActionContext;
import edu.baoss.orderservice.model.dtos.OrderValue;
import edu.baoss.orderservice.exceptions.AddressIsConnectedToNetworkException;
import edu.baoss.orderservice.feign.ResourceServiceFeignProxy;
import lombok.AllArgsConstructor;

public class AddressConnectionCheckAction extends FeasibilityCheckAction {

    @Override
    public void check(ActionContext context) {
        if (context.getApplicationContext().getBean(ResourceServiceFeignProxy.class)
                .isAddressConnected(context.getOrderValue().getSelectedAddress().getAddressId())) {
            throw new AddressIsConnectedToNetworkException("Internet product is already provided for this address!");
        }
    }
}

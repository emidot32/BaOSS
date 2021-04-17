package edu.baoss.orderservice.actions.checkers.internet;

import edu.baoss.orderservice.actions.checkers.CheckAction;
import edu.baoss.orderservice.dtos.OrderValue;
import edu.baoss.orderservice.exceptions.HomeIsNotConnectedToNetworkException;
import edu.baoss.orderservice.feign.ResourceServiceProxy;
import edu.baoss.orderservice.model.Address;
import edu.baoss.orderservice.services.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class HomeConnectionToNetworkCheckAction implements CheckAction {
    AddressService addressService;
    ResourceServiceProxy resourceServiceProxy;

    @Override
    public void check(OrderValue orderValue) {
        List<Long> addressIds = addressService.getAddressesOfOneBuilding(orderValue.getSelectedAddress())
                .stream()
                .map(Address::getId)
                .collect(Collectors.toList());
        if (!resourceServiceProxy.isBuildingConnectedToNetwork(addressIds))
            throw new HomeIsNotConnectedToNetworkException("The building is not connected to the network");
    }
}

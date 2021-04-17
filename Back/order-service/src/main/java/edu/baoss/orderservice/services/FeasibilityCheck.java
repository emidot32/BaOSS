package edu.baoss.orderservice.services;

import edu.baoss.orderservice.actions.checkers.CheckAction;
import edu.baoss.orderservice.actions.checkers.CommonCheckAction;
import edu.baoss.orderservice.actions.checkers.PriceCheckAction;
import edu.baoss.orderservice.actions.checkers.dtv.CommonDtvCheckAction;
import edu.baoss.orderservice.actions.checkers.internet.CommonInternetCheckAction;
import edu.baoss.orderservice.actions.checkers.internet.DeviceAvailabilityCheckAction;
import edu.baoss.orderservice.actions.checkers.internet.HomeConnectionToNetworkCheckAction;
import edu.baoss.orderservice.actions.checkers.mobile.CommonMobileCheckAction;
import edu.baoss.orderservice.actions.checkers.mobile.SimCardAvailabilityCheckAction;
import edu.baoss.orderservice.dtos.OrderValue;
import edu.baoss.orderservice.feign.ResourceServiceProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static edu.baoss.orderservice.services.Constants.*;

@Service
public class FeasibilityCheck {
    @Autowired
    AddressService addressService;
    @Autowired
    ResourceServiceProxy resourceServiceProxy;

    List<CheckAction> mobileCheckActionList = Arrays.asList(
            new CommonMobileCheckAction(), new SimCardAvailabilityCheckAction());
    List<CheckAction> internetCheckActionList = new ArrayList<>(Arrays.asList(
            new CommonInternetCheckAction(), new DeviceAvailabilityCheckAction()));
    List<CheckAction> commonCheckActionList = new ArrayList<>(Arrays.asList(
            new CommonCheckAction(), new PriceCheckAction()));

    public void feasibilityCheck(OrderValue orderValue) {
        internetCheckActionList.add(new HomeConnectionToNetworkCheckAction(addressService, resourceServiceProxy));
        for (String product: orderValue.getSelectedProducts()) {
            if (MOBILE_PRODUCT_STR.equals(product)) {
                commonCheckActionList.addAll(mobileCheckActionList);
            }
            if (INTERNET_PRODUCT_STR.equals(product)) {
                commonCheckActionList.addAll(internetCheckActionList);
            }
            if (DTV_PRODUCT_STR.equals(product)) {
                commonCheckActionList.add(new CommonDtvCheckAction());
            }
        }
        checkForProduct(commonCheckActionList, orderValue);
    }

    private void checkForProduct(List<CheckAction> checkActionList, final OrderValue orderValue) {
        for (CheckAction checkAction: checkActionList) {
            checkAction.check(orderValue);
        }
    }
}

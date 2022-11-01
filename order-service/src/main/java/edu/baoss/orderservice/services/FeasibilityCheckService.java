package edu.baoss.orderservice.services;

import edu.baoss.orderservice.actions.checking.FeasibilityCheckAction;
import edu.baoss.orderservice.actions.checking.CommonCheckAction;
import edu.baoss.orderservice.actions.checking.NrcPaymentAvailabilityCheckAction;
import edu.baoss.orderservice.actions.checking.PriceCheckAction;
import edu.baoss.orderservice.actions.checking.dtv.CommonDtvCheckAction;
import edu.baoss.orderservice.actions.checking.internet.AddressConnectionCheckAction;
import edu.baoss.orderservice.actions.checking.internet.CommonInternetCheckAction;
import edu.baoss.orderservice.actions.checking.internet.DeviceAvailabilityCheckAction;
import edu.baoss.orderservice.actions.checking.internet.HomeConnectionToNetworkCheckAction;
import edu.baoss.orderservice.actions.checking.mobile.CommonMobileCheckAction;
import edu.baoss.orderservice.actions.checking.mobile.SimCardAvailabilityCheckAction;
import edu.baoss.orderservice.model.ActionContext;
import edu.baoss.orderservice.model.dtos.OrderValue;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static edu.baoss.orderservice.Constants.*;

@Service
@RequiredArgsConstructor
public class FeasibilityCheckService {
    @Lazy
    private final ApplicationContext applicationContext;

    private final List<FeasibilityCheckAction> mobileCheckActionList = List.of(
            new CommonMobileCheckAction(), new SimCardAvailabilityCheckAction());
    private final List<FeasibilityCheckAction> internetCheckActionList = List.of(
            new CommonInternetCheckAction(), new DeviceAvailabilityCheckAction(),
            new HomeConnectionToNetworkCheckAction(), new AddressConnectionCheckAction());
    private final List<FeasibilityCheckAction> commonCheckActionList =  List.of(
            new CommonCheckAction(), new PriceCheckAction(), new NrcPaymentAvailabilityCheckAction());

    public void feasibilityCheck(OrderValue orderValue) {
        List<FeasibilityCheckAction> checkActions = new ArrayList<>(commonCheckActionList);
        for (String product: orderValue.getSelectedProducts()) {
            if (MOBILE_PRODUCT_STR.equals(product)) {
                checkActions.addAll(mobileCheckActionList);
            }
            if (INTERNET_PRODUCT_STR.equals(product)) {
                checkActions.addAll(internetCheckActionList);
            }
            if (DTV_PRODUCT_STR.equals(product)) {
                checkActions.add(new CommonDtvCheckAction());
            }
        }
        checkActions.forEach(checkAction -> checkAction.check(new ActionContext(orderValue, applicationContext)));
    }

}

package edu.baoss.billingservice.feign;

import edu.baoss.billingservice.model.dtos.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name="order-service", url = "${tb-api.url}/order-service")
public interface OrderServiceFeignProxy {
    @GetMapping("/instances/mobile-products")
    List<MobileProductInstance> getMobileProductInstances();

    @GetMapping("/instances/internet-products")
    List<InternetProductInstance> getInternetProductInstances();

    @GetMapping("/instances/dtv-products")
    List<DtvProductInstance> getDtvProductInstances();

    default List<ProductInstance> getActiveProductInstances() {
        List<ProductInstance> activeProductInstances = new ArrayList<>();
        activeProductInstances.addAll(getMobileProductInstances());
        activeProductInstances.addAll(getInternetProductInstances());
        activeProductInstances.addAll(getDtvProductInstances());
        return activeProductInstances;
    }

    @PutMapping("/instances/{instanceId}/inactivate")
    void inactivateInstance(@PathVariable long instanceId);

    @GetMapping("/orders")
    List<OrderDto> getAllOrders();
}

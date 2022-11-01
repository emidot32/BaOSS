package edu.baoss.billingservice.feign;

import edu.baoss.billingservice.model.dtos.OrderDto;
import edu.baoss.billingservice.model.dtos.ProductInstance;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@FeignClient(name="order-service", url = "${tb-api.url}/order-service")
public interface OrderServiceFeignProxy {
    @GetMapping("/instances")
    List<ProductInstance> getActiveInstances();

    @PutMapping("/instances/{instanceId}/inactivate")
    void inactivateInstance(@PathVariable long instanceId);

    @GetMapping("/orders")
    List<OrderDto> getAllOrders();
}

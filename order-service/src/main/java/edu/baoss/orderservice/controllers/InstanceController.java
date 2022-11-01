package edu.baoss.orderservice.controllers;

import edu.baoss.orderservice.model.dtos.DtvProductInstance;
import edu.baoss.orderservice.model.dtos.InternetProductInstance;
import edu.baoss.orderservice.model.dtos.MobileProductInstance;
import edu.baoss.orderservice.model.dtos.ProductInstance;
import edu.baoss.orderservice.services.InstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:5555", "http://localhost:4200"})
@RequestMapping("/instances")
public class InstanceController {

    @Autowired
    InstanceService instanceService;

    @GetMapping("/user/{userId}")
    List<ProductInstance> getAllInstancesForUser(@PathVariable long userId) {
        return instanceService.getAllProductInstancesForUser(userId);
    }
    @GetMapping
    List<ProductInstance> getActiveInstances() {
        return instanceService.getActiveProductInstances();
    }
    
    @GetMapping("/mobile-product/{mobileProductId}")
    MobileProductInstance getMobileProductInstance(@PathVariable("mobileProductId") long mobileProductId) {
        return instanceService.getMobileProductInstance(mobileProductId);
    }

    @GetMapping("/internet-product/{internetProductId}")
    InternetProductInstance getInternetProductInstance(@PathVariable("internetProductId") long internetProductId) {
        return instanceService.getInternetProductInstance(internetProductId);
    }

    @GetMapping("/dtv-product/{dtvProductId}")
    DtvProductInstance getDtvProductInstance(@PathVariable("dtvProductId") long dtvProductId) {
        return instanceService.getDtvProductInstance(dtvProductId);
    }

    @PutMapping("/{instanceId}/inactivate")
    void inactivateInstance(@PathVariable long instanceId) {
        instanceService.inactivateInstance(instanceId);
    }
}

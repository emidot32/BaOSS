package edu.baoss.orderservice.controllers;

import edu.baoss.orderservice.model.dtos.DeliveryDto;
import edu.baoss.orderservice.model.dtos.OrderValue;
import edu.baoss.orderservice.services.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

import static edu.baoss.orderservice.Constants.ONLY_DATE_FORMAT;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/deliveries")
public class DeliveryController {
    @Autowired
    DeliveryService deliveryService;

    @GetMapping("/available-times")
    public List<String> getAvailableHours(@RequestParam String deliveryDate, String[] products) throws ParseException {
        return deliveryService.getAvailableHours(ONLY_DATE_FORMAT.parse(deliveryDate), products);
    }

    @GetMapping("/{userId}")
    public List<DeliveryDto> getTodayDeliveriesForEmployee(@PathVariable long userId) {
        return deliveryService.getTodayDeliveriesForEmployeeAndUpdateIfStarted(userId);
    }

    @PutMapping("/finish")
    public DeliveryDto deliveryFinishEvent(@RequestBody OrderValue orderValue) {
        return deliveryService.finishDelivery(orderValue);
    }
}


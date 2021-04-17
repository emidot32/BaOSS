package edu.baoss.orderservice.controllers;

import edu.baoss.orderservice.services.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import static edu.baoss.orderservice.services.Constants.onlyDateFormat;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/delivery")
public class DeliveryController {
    @Autowired
    DeliveryService deliveryService;

    @GetMapping("available-times")
    public List<String> getAvailableHours(@RequestParam String deliveryDate, String[] products) throws ParseException {
        return deliveryService.getAvailableHours(onlyDateFormat.parse(deliveryDate), products);
    }
}


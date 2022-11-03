package edu.baoss.resourceservice.controllers;

import edu.baoss.resourceservice.model.PhoneNumber;
import edu.baoss.resourceservice.services.PhoneNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/phone-numbers")
public class PhoneNumberController {
    @Autowired
    PhoneNumberService phoneNumberService;

    @GetMapping("/all")
    public List<PhoneNumber> getPhoneNumbers(){
        return phoneNumberService.getPhoneNumbersForSale();
    }

    @GetMapping("/for-order-entry")
    public List<PhoneNumber> getPartPhoneNumbers(){
        return phoneNumberService.getPhoneNumbersForSale().stream()
                .limit(100)
                .collect(Collectors.toList());
    }

    @GetMapping("/{phoneNumber}")
    public PhoneNumber getPhoneNumber(@PathVariable("phoneNumber") String phoneNumberStr) {
        return phoneNumberService.getPhoneNumber(phoneNumberStr);
    }

    @PutMapping("/reserve-sim-card")
    PhoneNumber reserveSimCard(@RequestBody PhoneNumber phoneNumber) {
        return phoneNumberService.reserveSimCard(phoneNumber);
    }
}

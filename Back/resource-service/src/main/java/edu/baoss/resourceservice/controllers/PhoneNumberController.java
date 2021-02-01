package edu.baoss.resourceservice.controllers;

import edu.baoss.resourceservice.model.Device;
import edu.baoss.resourceservice.model.PhoneNumber;
import edu.baoss.resourceservice.services.DeviceService;
import edu.baoss.resourceservice.services.PhoneNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/phone-numbers")
public class PhoneNumberController {
    @Autowired
    PhoneNumberService phoneNumberService;

    @GetMapping()
    public List<PhoneNumber> getDevicesForSale(){
        return phoneNumberService.getPhoneNumbersForSale();
    }
}

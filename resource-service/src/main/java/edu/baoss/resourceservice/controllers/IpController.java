package edu.baoss.resourceservice.controllers;

import edu.baoss.resourceservice.services.IpAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/fixed-ip")
public class IpController {
    @Autowired
    IpAddressService ipAddressService;
    @GetMapping
    String getFixedIP() {
        return ipAddressService.getFixedIp();
    }
}

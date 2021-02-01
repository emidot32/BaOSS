package edu.baoss.resourceservice.controllers;

import edu.baoss.resourceservice.model.Device;
import edu.baoss.resourceservice.services.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/devices")
public class DeviceController {
    @Autowired
    DeviceService deviceService;

    @GetMapping()
    public List<Device> getDevicesForSale(){
        return deviceService.getDevicesForSale();
    }
}

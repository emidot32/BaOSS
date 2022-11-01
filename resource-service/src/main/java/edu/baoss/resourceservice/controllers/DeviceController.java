package edu.baoss.resourceservice.controllers;

import edu.baoss.resourceservice.dtos.UserDevice;
import edu.baoss.resourceservice.model.Device;
import edu.baoss.resourceservice.services.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{deviceId}")
    public UserDevice getUserDeviceByDeviceId(@PathVariable("deviceId") String deviceId,
                                              @RequestParam("addressId") long addressId) {
        return deviceService.getUserDeviceByDeviceId(deviceId, addressId);
    }

    @GetMapping("/reserved-device")
    public UserDevice getReservedDeviceByDeviceId(@RequestParam("deviceId") String deviceId) {
        return deviceService.getReservedDevice(deviceId);
    }

    @PutMapping("/reserve-device")
    UserDevice reserveDevice(@RequestParam("deviceId") String deviceId) {
        return deviceService.reserveDevice(deviceId);
    }
}

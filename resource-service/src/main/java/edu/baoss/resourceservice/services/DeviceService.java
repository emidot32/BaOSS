package edu.baoss.resourceservice.services;

import edu.baoss.resourceservice.dtos.UserDevice;
import edu.baoss.resourceservice.exceptions.NoDeviceFoundException;
import edu.baoss.resourceservice.feignproxies.OfferFeignProxy;
import edu.baoss.resourceservice.model.Device;
import edu.baoss.resourceservice.repositories.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeviceService {
    @Autowired
    DeviceRepository deviceRepository;
    @Autowired
    OfferFeignProxy offerFeignProxy;
    @Autowired
    ConnectionService connectionService;

    public List<Device> getDevicesForSale() {
        return deviceRepository.findAll(Sort.by(Sort.Direction.DESC, "price"))
                .stream()
                .filter(device -> device.isForSale() && device.getType().equals("Router") && hasInstance(device))
                .peek(device -> device.setPrice(offerFeignProxy.getDiscountedPrice("Devices", device.getPrice())))
                .collect(Collectors.toList());
    }

    public UserDevice getUserDeviceByDeviceId(String deviceId, long addressId) {
        System.out.println(deviceId);
        Device device = deviceRepository.findById(deviceId).orElseThrow(NoDeviceFoundException::new);
        System.out.println(device.getName());
        String macAddress = connectionService.getMacAddressByAddressId(addressId);
        int index = -1;
        String[] macAddresses = device.getMacAddresses();
        for (int i = 0; i < macAddresses.length; i++) {
            if (macAddresses[i].equals(macAddress))
                index = i;
        }
        UserDevice userDevice = new UserDevice(device);
        userDevice.setMacAddress(macAddress);
        userDevice.setSerialNumber(device.getSerialNumbers()[index]);
        return userDevice;
    }

    public UserDevice getReservedDevice(String deviceId) {
        Device device = deviceRepository.findById(deviceId).orElseThrow(NoDeviceFoundException::new);
        System.out.println(device.getName());
        int index = getFirstFreeInstance(device) - 1;
        UserDevice userDevice = new UserDevice(device);
        userDevice.setMacAddress(device.getMacAddresses()[index]);
        userDevice.setSerialNumber(device.getSerialNumbers()[index]);
        return userDevice;
    }

    public UserDevice reserveDevice(String deviceId) {
        Device device = deviceRepository.findById(deviceId).orElseThrow(NoDeviceFoundException::new);
        int index = getFirstFreeInstance(device);
        System.out.println(index);
        System.out.println(Arrays.toString(device.getUsed()));
        device.getUsed()[index] = true;
        System.out.println(Arrays.toString(device.getUsed()));
        deviceRepository.save(device);
        UserDevice userDevice = new UserDevice(device);
        userDevice.setSerialNumber(device.getSerialNumbers()[index]);
        userDevice.setMacAddress(device.getMacAddresses()[index]);
        return userDevice;
    }

    boolean hasInstance(Device device) {
        return getFirstFreeInstance(device) != -1;
    }

    int getFirstFreeInstance(Device device) {
        for (int i = 0; i < device.getUsed().length; i++) {
            if (!device.getUsed()[i])
                return i;
        }
        return -1;
    }

}

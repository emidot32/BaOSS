package edu.baoss.resourceservice.services;

import edu.baoss.resourceservice.feignproxies.OfferFeignProxy;
import edu.baoss.resourceservice.model.Device;
import edu.baoss.resourceservice.repositories.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeviceService {
    @Autowired
    DeviceRepository deviceRepository;
    @Autowired
    OfferFeignProxy offerFeignProxy;

    public List<Device> getDevicesForSale() {
        return deviceRepository.findAll(Sort.by(Sort.Direction.DESC, "price"))
                .stream()
                .filter(device -> device.isForSale() && device.getType().equals("Router") && hasInstance(device))
                .peek(device -> device.setPrice(offerFeignProxy.getDiscountedPrice("Devices", device.getPrice())))
                .collect(Collectors.toList());
    }

    boolean hasInstance(Device device) {
        return getFirstFreeInstance(device) != -1;
    }

    int getFirstFreeInstance(Device device) {
        for (int i = 0; i < device.getUsed().length; i++) {
            if (!device.getUsed()[i]) return i;
        }
        return -1;
    }

}

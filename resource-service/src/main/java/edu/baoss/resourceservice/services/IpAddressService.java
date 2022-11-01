package edu.baoss.resourceservice.services;

import edu.baoss.resourceservice.model.IpAddress;
import edu.baoss.resourceservice.repositories.IpAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IpAddressService {
    @Autowired
    IpAddressRepository ipAddressRepository;

    public String getFixedIp() {
        IpAddress ipAddress = ipAddressRepository.getFirstByUsed(false).orElse(null);
        if (ipAddress != null) {
            ipAddress.setUsed(true);
            ipAddressRepository.save(ipAddress);
            return ipAddress.getIpAddress();
        }
        return null;
    }
}

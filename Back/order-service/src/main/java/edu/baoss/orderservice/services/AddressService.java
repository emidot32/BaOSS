package edu.baoss.orderservice.services;

import edu.baoss.orderservice.model.Address;
import edu.baoss.orderservice.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressService {
    @Autowired
    AddressRepository addressRepository;

    public List<Address> getAddressesOfOneBuilding(Address userAddress) {
        return addressRepository.findAll()
                .stream()
                .filter(address -> address.equalsByBuilding(userAddress))
                .collect(Collectors.toList());
    }
}

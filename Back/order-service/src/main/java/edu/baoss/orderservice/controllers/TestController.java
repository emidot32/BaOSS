package edu.baoss.orderservice.controllers;

import edu.baoss.orderservice.model.Address;
import edu.baoss.orderservice.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    AddressRepository addressRepository;

    @GetMapping("/plus")
    public int plus(@RequestParam("a") int a, @RequestParam("b") int b){
        return a+b;
    }
    
    @GetMapping("/address/{id}")
    public Address plus(@PathVariable long id){
        return addressRepository.findById(id).get();
    }

    @PostMapping("/address")
    public Address plus(@RequestBody Address addressBody){
        Address address = new Address();
        System.out.println(addressBody);
        address.setCountry(addressBody.getCountry());
        address.setCity(addressBody.getCity());
        address.setStreet(addressBody.getStreet());
        address.setBuildingNum(addressBody.getBuildingNum());
        address.setRoomNum(addressBody.getRoomNum());
        return addressRepository.save(address);
    }
}

package edu.baoss.resourceservice.controllers;

import edu.baoss.resourceservice.services.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/connection")
public class ConnectionController {
    @Autowired
    ConnectionService connectionService;

    @GetMapping("/connected-buildings")
    public boolean isBuildingConnectedToNetwork(@RequestParam long buildingId) {
        System.out.println(connectionService.isBuildingConnectedToNetwork(buildingId));
        return connectionService.isBuildingConnectedToNetwork(buildingId);
    }

    @GetMapping("/connected-address")
    public boolean isAddressConnected(@RequestParam long addressId) {
        return connectionService.isAddressConnected(addressId);
    }

    @PutMapping("/connect-user")
    void connectUser(@RequestParam("buildingId") long buildingId,
                     @RequestParam("addressId") long addressId,
                     @RequestParam("macAddress") String macAddress) {
        connectionService.connectUser(buildingId, addressId, macAddress);
    }

    @PostMapping("/connect-buildings")
    void connectBuildings(@RequestBody List<Long> buildingIds) {
        connectionService.connectBuildings(buildingIds);
    }
}

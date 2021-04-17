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
    public boolean isBuildingConnectedToNetwork(@RequestParam List<Long> addressIdsOfBuilding) {
        return connectionService.isBuildingConnectedToNetwork(addressIdsOfBuilding);
    }
}

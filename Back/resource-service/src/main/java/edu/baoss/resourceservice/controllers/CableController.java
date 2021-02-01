package edu.baoss.resourceservice.controllers;

import edu.baoss.resourceservice.model.Cable;
import edu.baoss.resourceservice.services.CableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/cables")
public class CableController {
    @Autowired
    CableService cableService;

    @GetMapping("twisted-pair-cable")
    public Cable getTwistedPairCable() {
        return cableService.getTwistedPairCable();
    }
}

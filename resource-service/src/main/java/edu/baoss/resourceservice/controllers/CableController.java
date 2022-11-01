package edu.baoss.resourceservice.controllers;

import edu.baoss.resourceservice.model.Cable;
import edu.baoss.resourceservice.services.CableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/cables")
public class CableController {
    @Autowired
    CableService cableService;

    @GetMapping
    public String get() {
        return "werfsefd";
    }

    @GetMapping("/twisted-pair-cable")
    public Cable getTwistedPairCable() {
        return cableService.getTwistedPairCable();
    }

    @PutMapping()
    void reserveCable(@RequestParam("cableLength") int cableLength) {
        cableService.reserveCable(cableLength);
    }
}

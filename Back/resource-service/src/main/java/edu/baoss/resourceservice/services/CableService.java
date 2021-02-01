package edu.baoss.resourceservice.services;

import edu.baoss.resourceservice.exceptions.NoCableFoundException;
import edu.baoss.resourceservice.model.Cable;
import edu.baoss.resourceservice.repositories.CableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CableService {
    @Autowired
    CableRepository cableRepository;

    public Cable getTwistedPairCable() {
        Cable twistedPair = cableRepository.findAll()
                .stream()
                .filter(cable -> cable.getType().equals("twisted pair"))
                .findFirst()
                .orElseThrow(() -> new NoCableFoundException("Sorry, but we don't have a cable yet ("));
        if (twistedPair.getLength() < 3) {
            throw new NoCableFoundException("Sorry, but we don't have a cable yet (");
        }
        return twistedPair;
    }
}

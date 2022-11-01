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
        Cable twistedPair = cableRepository.getCableByType("twisted pair").orElseThrow(NoCableFoundException::new);
        if (twistedPair.getLength() < 3) {
            throw new NoCableFoundException();
        }
        return twistedPair;
    }

    public void reserveCable(int cableLength) {
        Cable twistedPair = cableRepository.getCableByType("twisted pair").orElseThrow(NoCableFoundException::new);
        twistedPair.setLength(twistedPair.getLength()-cableLength);
        cableRepository.save(twistedPair);
    }
}

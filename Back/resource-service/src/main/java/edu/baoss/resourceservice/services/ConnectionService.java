package edu.baoss.resourceservice.services;

import edu.baoss.resourceservice.model.ConnectedAddress;
import edu.baoss.resourceservice.repositories.ConnectedBuildingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConnectionService {
    @Autowired
    ConnectedBuildingsRepository connectedBuildingsRepository;

    public boolean isBuildingConnectedToNetwork(List<Long> addressIdsOfBuilding) {
       return connectedBuildingsRepository.findAll()
                .stream()
                .flatMap(connectedBuilding -> connectedBuilding.getConnectedAddresses().stream())
                .map(ConnectedAddress::getAddressId)
                .anyMatch(addressIdsOfBuilding::contains);
    }
}

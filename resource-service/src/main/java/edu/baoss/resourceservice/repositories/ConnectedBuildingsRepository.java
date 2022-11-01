package edu.baoss.resourceservice.repositories;

import edu.baoss.resourceservice.model.ConnectedBuilding;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ConnectedBuildingsRepository extends MongoRepository<ConnectedBuilding, String> {
    Optional<ConnectedBuilding> findConnectedBuildingByBuildingId(long buildingId);
}

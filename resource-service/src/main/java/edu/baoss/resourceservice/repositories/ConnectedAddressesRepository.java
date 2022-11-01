package edu.baoss.resourceservice.repositories;

import edu.baoss.resourceservice.model.ConnectedAddress;
import edu.baoss.resourceservice.model.ConnectedBuilding;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConnectedAddressesRepository extends MongoRepository<ConnectedAddress, String> {
}

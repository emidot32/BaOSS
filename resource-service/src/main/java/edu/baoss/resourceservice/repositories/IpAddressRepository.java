package edu.baoss.resourceservice.repositories;

import edu.baoss.resourceservice.model.IpAddress;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface IpAddressRepository extends MongoRepository<IpAddress, String> {
    Optional<IpAddress> getFirstByUsed(boolean used);
}

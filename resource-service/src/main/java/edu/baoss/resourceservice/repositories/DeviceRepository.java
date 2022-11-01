package edu.baoss.resourceservice.repositories;

import edu.baoss.resourceservice.model.Device;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DeviceRepository extends MongoRepository<Device, String> {

}

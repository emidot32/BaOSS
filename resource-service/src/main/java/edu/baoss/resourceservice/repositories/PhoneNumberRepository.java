package edu.baoss.resourceservice.repositories;

import edu.baoss.resourceservice.model.PhoneNumber;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhoneNumberRepository extends MongoRepository<PhoneNumber, String> {
    Optional<PhoneNumber> getByPhoneNumber(String phoneNumber);
}

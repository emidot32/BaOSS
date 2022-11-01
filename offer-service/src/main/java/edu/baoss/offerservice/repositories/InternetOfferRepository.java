package edu.baoss.offerservice.repositories;

import edu.baoss.offerservice.model.InternetOffer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InternetOfferRepository extends MongoRepository<InternetOffer, String> {
    Optional<InternetOffer> getById(String id);
}

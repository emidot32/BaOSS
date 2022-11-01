package edu.baoss.offerservice.repositories;

import edu.baoss.offerservice.model.DtvOffer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DtvOfferRepository extends MongoRepository<DtvOffer, String> {
    Optional<DtvOffer> getById(String id);
}

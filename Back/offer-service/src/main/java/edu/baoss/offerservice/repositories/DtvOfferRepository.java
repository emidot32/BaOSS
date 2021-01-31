package edu.baoss.offerservice.repositories;

import edu.baoss.offerservice.model.DtvOffer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DtvOfferRepository extends MongoRepository<DtvOffer, String> {
}

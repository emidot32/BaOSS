package edu.baoss.offerservice.repositories;

import edu.baoss.offerservice.model.Tariff;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TariffRepository extends MongoRepository<Tariff, String> {
}

package edu.baoss.offerservice.repositories;

import edu.baoss.offerservice.model.ConstantPrices;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConstantPricesRepository extends MongoRepository<ConstantPrices, String> {
}

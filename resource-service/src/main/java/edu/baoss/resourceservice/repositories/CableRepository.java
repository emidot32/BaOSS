package edu.baoss.resourceservice.repositories;

import edu.baoss.resourceservice.model.Cable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CableRepository extends MongoRepository<Cable, String> {
    Optional<Cable> getCableByType(String type);

}

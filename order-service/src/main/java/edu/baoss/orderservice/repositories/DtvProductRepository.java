package edu.baoss.orderservice.repositories;

import edu.baoss.orderservice.model.entities.DtvProduct;
import edu.baoss.orderservice.model.entities.Instance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DtvProductRepository extends JpaRepository<DtvProduct, Long> {
    Optional<DtvProduct> getDtvProductByInstance(Instance instance);
}

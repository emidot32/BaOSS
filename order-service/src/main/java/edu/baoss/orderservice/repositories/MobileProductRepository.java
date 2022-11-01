package edu.baoss.orderservice.repositories;

import edu.baoss.orderservice.model.entities.Instance;
import edu.baoss.orderservice.model.entities.MobileProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MobileProductRepository extends JpaRepository<MobileProduct, Long> {
    Optional<MobileProduct> getMobileProductByInstance(Instance instance);
}

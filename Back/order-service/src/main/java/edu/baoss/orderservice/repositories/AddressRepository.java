package edu.baoss.orderservice.repositories;

import edu.baoss.orderservice.model.Address;
import jdk.jfr.Registered;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends CrudRepository<Address, Long> {
}

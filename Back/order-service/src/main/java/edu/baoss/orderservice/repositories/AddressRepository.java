package edu.baoss.orderservice.repositories;

import edu.baoss.orderservice.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query(value = "select a.* from address a " +
            "inner join user_address ua using (address_id) where user_id = :userId", nativeQuery = true)
    Address getAddressByUserId(@Param("userId") long userId);

}

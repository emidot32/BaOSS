package edu.baoss.userservice.repository;

import edu.baoss.userservice.model.Address;
import edu.baoss.userservice.model.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingRepository extends JpaRepository<Building, Long> {

}

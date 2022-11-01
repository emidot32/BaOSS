package edu.baoss.orderservice.repositories;

import edu.baoss.orderservice.model.entities.Address;
import edu.baoss.orderservice.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "select reg_date from users where user_id = :userId", nativeQuery = true)
    Date getRegDateByUserId(@Param("userId") long userId);

}

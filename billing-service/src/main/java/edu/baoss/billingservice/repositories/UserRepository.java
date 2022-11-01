package edu.baoss.billingservice.repositories;

import edu.baoss.billingservice.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User getUserByLogin(String login);

    @Modifying
    @Query(value = "update users set balance = balance - :totalNrc where user_id = :userId", nativeQuery = true)
    void doNrcPayment(@Param("userId") long userId, @Param("totalNrc") double totalNrc);

    @Modifying
    @Query(value = "update users set balance = balance - :totalMrc where user_id = :userId", nativeQuery = true)
    void doMrcPayment(@Param("userId") long userId, @Param("totalMrc") double totalMrc);
}

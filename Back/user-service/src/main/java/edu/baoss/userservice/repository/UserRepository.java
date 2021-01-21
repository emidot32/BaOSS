package edu.baoss.userservice.repository;

import edu.baoss.userservice.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User getUserByLogin(String login);
    boolean existsUserByLogin(String login);
    boolean existsUserByEmail(String email);
    boolean existsUserByIdCardNumber(String idCardNumber);

    @Query(value = "select max(contract_number) from usr", nativeQuery = true)
    int getMaxContractNumber();

    List<User> findAll(Sort id);
}

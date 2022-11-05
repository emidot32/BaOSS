package edu.baoss.userservice.repository;

import edu.baoss.userservice.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getUserByLogin(String login);

    @Query(value = "select password from users where login = :login", nativeQuery = true)
    String getPasswordByLogin(@Param("login") String login);
    boolean existsUserByLogin(String login);
    boolean existsUserByEmail(String email);
    boolean existsUserByIdCardNumber(String idCardNumber);

    @Modifying
    @Transactional
    @Query(value = "delete from user_address where user_id = :userId and address_id = :addressId", nativeQuery = true)
    void deleteAddressForUser(@Param("userId") long userId, @Param("addressId") long addressId);

    @Query(value = "select max(contract_number) from users", nativeQuery = true)
    int getMaxContractNumber();

    List<User> findAll(Sort id);

}

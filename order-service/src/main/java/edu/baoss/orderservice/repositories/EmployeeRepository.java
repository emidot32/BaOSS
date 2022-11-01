package edu.baoss.orderservice.repositories;

import edu.baoss.orderservice.model.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findAllByPosition(String position);
    Optional<Employee> getEmployeeByUserId(long userId);
}

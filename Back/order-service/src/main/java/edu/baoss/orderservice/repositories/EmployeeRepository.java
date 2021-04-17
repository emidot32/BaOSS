package edu.baoss.orderservice.repositories;

import edu.baoss.orderservice.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findAllByPosition(String position);
}

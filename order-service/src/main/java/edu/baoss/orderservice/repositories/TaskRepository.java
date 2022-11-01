package edu.baoss.orderservice.repositories;

import edu.baoss.orderservice.model.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}

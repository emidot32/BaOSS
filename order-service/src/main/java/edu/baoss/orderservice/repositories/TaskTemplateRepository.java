package edu.baoss.orderservice.repositories;

import edu.baoss.orderservice.model.entities.TaskTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskTemplateRepository extends JpaRepository<TaskTemplate, Long> {
}

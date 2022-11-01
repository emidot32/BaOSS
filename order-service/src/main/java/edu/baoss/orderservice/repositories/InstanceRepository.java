package edu.baoss.orderservice.repositories;

import edu.baoss.orderservice.model.entities.Instance;
import edu.baoss.orderservice.model.enums.InstanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface InstanceRepository extends JpaRepository<Instance, Long> {

    Set<Instance> getInstancesByUserId(long userId);

    List<Instance> getInstanceByStatus(InstanceStatus status);

    @Modifying
    @Query(value = "update instances set status = 'INACTIVE' where id = :instanceId", nativeQuery = true)
    void inactivateInstance(@Param("instanceId") long instanceId);
}

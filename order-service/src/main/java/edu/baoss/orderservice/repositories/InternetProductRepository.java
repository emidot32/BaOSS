package edu.baoss.orderservice.repositories;

import edu.baoss.orderservice.model.entities.Instance;
import edu.baoss.orderservice.model.entities.InternetProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.Optional;

public interface InternetProductRepository extends JpaRepository<InternetProduct, Long> {
    Optional<InternetProduct> getInternetProductByInstance(Instance instance);

    @Modifying(clearAutomatically = true)
    @Query(value = "update internet_product set device = :deviceId where inet_prod_id = :internetProductId", nativeQuery = true)
    void updateDeviceIdForInternetProduct(@Param("internetProductId") long internetProductId,
                                          @Param("deviceId") String deviceId);

    @Query(value = "update internet_product set fixed_ip = :fixedIp where inet_prod_id = :internetProductId", nativeQuery = true)
    void setFixedIpForInternetProduct(@Param("internetProductId") long internetProductId,
                                      @Param("fixedIp") String fixedIp);

    Optional<InternetProduct> getInternetProductByInstanceId(long instanceId);
}

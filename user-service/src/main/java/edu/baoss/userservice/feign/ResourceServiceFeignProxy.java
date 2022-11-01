package edu.baoss.userservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name="resource-service")
public interface ResourceServiceFeignProxy {
    @PostMapping("/connection/connect-buildings")
    void connectBuildings(@RequestBody List<Long> buildingIds);
}

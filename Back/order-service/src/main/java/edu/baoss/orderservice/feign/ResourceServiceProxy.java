package edu.baoss.orderservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name="resource-service", url="localhost:5555/resource-service/connection")
public interface ResourceServiceProxy {
    @GetMapping("/connected-buildings")
    boolean isBuildingConnectedToNetwork(@RequestParam("addressIdsOfBuilding") List<Long> addressIdsOfBuilding);
}

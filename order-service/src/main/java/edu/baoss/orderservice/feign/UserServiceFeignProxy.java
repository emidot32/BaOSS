package edu.baoss.orderservice.feign;

import edu.baoss.orderservice.model.dtos.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url="${tb-api.url}/user-service")
public interface UserServiceFeignProxy {

    @GetMapping("/users/{userId}")
    UserDto getUser(@PathVariable long userId);

}

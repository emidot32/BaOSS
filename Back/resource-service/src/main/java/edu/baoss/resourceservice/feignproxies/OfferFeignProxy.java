package edu.baoss.resourceservice.feignproxies;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="offer-service", url="localhost:5555/offer-service/offers")
public interface OfferFeignProxy {
    @GetMapping("/discounted-price")
    double getDiscountedPrice(@RequestParam("resourceName") String resourceName,
                              @RequestParam("startPrice") double startPrice);

}

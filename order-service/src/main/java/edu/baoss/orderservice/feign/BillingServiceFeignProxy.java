package edu.baoss.orderservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "billing-service", url="${tb-api.url}/billing-service")
public interface BillingServiceFeignProxy {
    @PutMapping("/nrc-payment")
    void doNrcPayment(@RequestParam("userId") long userId,
                      @RequestParam("totalNRC") double totalNRC);

    @GetMapping("/check-nrc-payment")
    boolean checkNrcPayment(@RequestParam("userId") long userId,
                            @RequestParam("totalNRC") double totalNRC);
}

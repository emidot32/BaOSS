package edu.baoss.orderservice.feign;

import edu.baoss.orderservice.model.dtos.ConstantPrices;
import edu.baoss.orderservice.model.dtos.DtvOffer;
import edu.baoss.orderservice.model.dtos.InternetOffer;
import edu.baoss.orderservice.model.dtos.Tariff;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "offer-service", url="${tb-api.url}/offer-service/offers")
public interface OfferServiceFeignProxy {

    @GetMapping("/tariff")
    Tariff getTariffById(@RequestParam("tariffId") String tariffId);

    @GetMapping("/internet-offer")
    InternetOffer getInternetOfferById(@RequestParam("internetOfferId") String internetOfferId);

    @GetMapping("/dtv-offer")
    DtvOffer getDtvOfferById(@RequestParam("dtvOfferId") String dtvOfferId);

    @GetMapping("/tariffs")
    List<Tariff> getTariffs();

    @GetMapping("/internet-offers")
    List<InternetOffer> getInternetOffers();

    @GetMapping("/dtv-offers")
    List<DtvOffer> getDtvOffers();

    @GetMapping("/constant-prices")
    ConstantPrices getConstantPrices();
}

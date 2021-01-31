package edu.baoss.offerservice.controllers;

import edu.baoss.offerservice.dto.ConstantPricesDto;
import edu.baoss.offerservice.dto.DtvOfferDto;
import edu.baoss.offerservice.dto.InternetOfferDto;
import edu.baoss.offerservice.dto.TariffDto;
import edu.baoss.offerservice.model.ConstantPrices;
import edu.baoss.offerservice.model.Discount;
import edu.baoss.offerservice.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/offers")
public class OffersController {
    @Autowired
    InternetOfferService internetOfferService;
    @Autowired
    DtvOfferService dtvOfferService;
    @Autowired
    TariffService tariffService;
    @Autowired
    ConstantPricesService constantPricesService;
    @Autowired
    DiscountService discountService;

    @GetMapping("/internet-offers")
    public List<InternetOfferDto> getInternetOffers() {
        return internetOfferService.getInternetOffers();
    }

    @GetMapping("/dtv-offers")
    public List<DtvOfferDto> getDtvOffers() {
        return dtvOfferService.getDtvOffers();
    }

    @GetMapping("/tariffs")
    public List<TariffDto> getTariffs() {
        return tariffService.getTariffs();
    }

    @GetMapping("/constant-prices")
    public ConstantPricesDto getConstantPrices() {
        return constantPricesService.getConstantPrices();
    }

    @GetMapping("/active-discounts")
    public List<Discount> getActiveDiscounts() {
        return discountService.getActiveDiscounts();
    }

    @GetMapping("/discounted-price")
    public double getDiscountedPrice(@RequestParam("resourceName") String resourceName, @RequestParam("startPrice") double startPrice ) {
        return discountService.getDiscountedPrice(startPrice, resourceName);
    }
}

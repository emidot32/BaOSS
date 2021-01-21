package edu.baoss.offerservice.controllers;

import edu.baoss.offerservice.dto.ConstantPricesDto;
import edu.baoss.offerservice.dto.DtvOfferDto;
import edu.baoss.offerservice.dto.InternetOfferDto;
import edu.baoss.offerservice.dto.TariffDto;
import edu.baoss.offerservice.model.ConstantPrices;
import edu.baoss.offerservice.services.ConstantPricesService;
import edu.baoss.offerservice.services.DtvOfferService;
import edu.baoss.offerservice.services.InternetOfferService;
import edu.baoss.offerservice.services.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

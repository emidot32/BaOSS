package edu.baoss.offerservice.services;

import edu.baoss.offerservice.dto.OfferDto;
import edu.baoss.offerservice.model.Discount;
import edu.baoss.offerservice.model.Offer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public abstract class OfferService {
    @Autowired
    DiscountService discountService;

    public List<OfferDto> getOffers(List<? extends Offer> offers) {
        Discount totalDiscount = discountService.getTotalDiscount(getProductName());
        return offers.stream()
                .sorted(Comparator.comparingDouble(Offer::getRent).reversed())
                .map(offer -> getOfferDto(offer, totalDiscount))
                .collect(Collectors.toList());
    }

    public OfferDto getOfferDto(Offer offer, Discount totalDiscount) {
        double startPrice = offer.getRent();
        double discountedPrice = discountService.calcDiscountedPrice(startPrice, totalDiscount);
        String priceEnding = Converter.getConvertedString(offer.getCurrency(), offer.getRentTime());
        OfferDto offerDto = createConcreteDto(offer);
        offerDto.setId(offer.getId());
        offerDto.setStartingPrice(offer.getRent());
        offerDto.setDiscountedPrice(discountedPrice);
        offerDto.setPriceEnding(priceEnding);
        offerDto.setDiscount(totalDiscount.getValue());
        offerDto.setDiscountEndDate(Constants.onlyDateFormat.format(totalDiscount.getEndDate()));
        offerDto.setCurrency(offer.getCurrency());
        offerDto.setRentTime(offer.getRentTime());
        return offerDto;
    }

    public abstract String getProductName();

    public abstract OfferDto createConcreteDto(Offer offer);
}

package edu.baoss.offerservice.services;

import edu.baoss.offerservice.dto.IOfferDto;
import edu.baoss.offerservice.dto.InternetOfferDto;
import edu.baoss.offerservice.model.Discount;
import edu.baoss.offerservice.model.IOffer;
import edu.baoss.offerservice.model.InternetOffer;
import edu.baoss.offerservice.repositories.InternetOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("internetService")
public class InternetOfferService extends OfferService {
    @Autowired
    InternetOfferRepository internetOfferRepository;

    public List<InternetOfferDto> getInternetOffers() {
        List<IOffer> offers = internetOfferRepository.findAll().stream()
                .map(offer -> (IOffer) offer)
                .collect(Collectors.toList());
        return getOffers(offers).stream()
                .map(offer -> (InternetOfferDto) offer)
                .collect(Collectors.toList());
    }

    @Override
    public String getProductName() {
        return "Internet product";
    }

    @Override
    public IOfferDto createDtoObject(IOffer offer, Discount discount,
                                     double discountedPrice, String priceEnding) {
        return InternetOfferDto.builder()
                .id(offer.getId())
                .speed(((InternetOffer) offer).getSpeed())
                .startingPrice(offer.getRent())
                .discountedPrice(discountedPrice)
                .priceEnding(priceEnding)
                .discount(discount.getValue())
                .discountEndDate(formatter.format(discount.getEndDate()))
                .build();
    }
}

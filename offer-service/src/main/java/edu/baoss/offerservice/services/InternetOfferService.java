package edu.baoss.offerservice.services;

import edu.baoss.offerservice.dto.InternetOfferDto;
import edu.baoss.offerservice.dto.OfferDto;
import edu.baoss.offerservice.exceptions.NoOfferFoundException;
import edu.baoss.offerservice.model.Discount;
import edu.baoss.offerservice.model.Offer;
import edu.baoss.offerservice.model.InternetOffer;
import edu.baoss.offerservice.repositories.InternetOfferRepository;
import edu.baoss.offerservice.util.StreamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("internetService")
public class InternetOfferService extends OfferService {
    @Autowired
    InternetOfferRepository internetOfferRepository;

    public List<InternetOfferDto> getInternetOffers() {
        List<? extends Offer> offers = internetOfferRepository.findAll();
        return StreamUtil.toStream(getOffers(offers))
                .map(InternetOfferDto.class::cast)
                .collect(Collectors.toList());
    }

    public InternetOfferDto getInternetOffer(String internetOfferId) {
        InternetOffer internetOffer = internetOfferRepository.getById(internetOfferId).orElseThrow(NoOfferFoundException::new);
        Discount totalDiscount = discountService.getTotalDiscount(getProductName());
        return (InternetOfferDto) getOfferDto(internetOffer, totalDiscount);
    }

    @Override
    public String getProductName() {
        return Constants.INTERNET_PRODUCT_STR;
    }

    @Override
    public OfferDto createConcreteDto(Offer offer) {
        return InternetOfferDto.builder()
                .speed(((InternetOffer) offer).getSpeed())
                .build();
    }
}

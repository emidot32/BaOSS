package edu.baoss.offerservice.services;

import edu.baoss.offerservice.dto.DtvOfferDto;
import edu.baoss.offerservice.dto.IOfferDto;
import edu.baoss.offerservice.model.Discount;
import edu.baoss.offerservice.model.DtvOffer;
import edu.baoss.offerservice.model.IOffer;
import edu.baoss.offerservice.repositories.DtvOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("dtvService")
public class DtvOfferService extends OfferService{
    @Autowired
    DtvOfferRepository dtvOfferRepository;

    public List<DtvOfferDto> getDtvOffers() {
        List<IOffer> offers = dtvOfferRepository.findAll().stream()
                                            .map(offer -> (IOffer) offer)
                                            .collect(Collectors.toList());
        return getOffers(offers).stream()
                .map(offer -> (DtvOfferDto) offer)
                .collect(Collectors.toList());
    }

    @Override
    public String getProductName() {
        return "DTV product";
    }

    @Override
    public IOfferDto createDtoObject(IOffer offer, Discount discount,
                                     double discountedPrice, String priceEnding) {
        return DtvOfferDto.builder()
                .id(offer.getId())
                .channelNumber(((DtvOffer) offer).getChannelNumber())
                .startingPrice(offer.getRent())
                .discountedPrice(discountedPrice)
                .priceEnding(priceEnding)
                .discount(discount.getValue())
                .discountEndDate(formatter.format(discount.getEndDate()))
                .build();
    }
}

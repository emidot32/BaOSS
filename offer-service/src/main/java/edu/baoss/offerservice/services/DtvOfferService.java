package edu.baoss.offerservice.services;

import edu.baoss.offerservice.dto.DtvOfferDto;
import edu.baoss.offerservice.dto.OfferDto;
import edu.baoss.offerservice.exceptions.NoOfferFoundException;
import edu.baoss.offerservice.model.Discount;
import edu.baoss.offerservice.model.DtvOffer;
import edu.baoss.offerservice.model.Offer;
import edu.baoss.offerservice.repositories.DtvOfferRepository;
import edu.baoss.offerservice.util.StreamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("dtvService")
public class DtvOfferService extends OfferService {
    @Autowired
    DtvOfferRepository dtvOfferRepository;

    public List<DtvOfferDto> getDtvOffers() {
        List<? extends Offer> offers = dtvOfferRepository.findAll();
        return StreamUtil.toStream(getOffers(offers))
                .map(DtvOfferDto.class::cast)
                .collect(Collectors.toList());
    }

    public DtvOfferDto getDtvOffer(String dtvOfferId) {
        DtvOffer dtvOffer = dtvOfferRepository.getById(dtvOfferId).orElseThrow(NoOfferFoundException::new);
        Discount totalDiscount = discountService.getTotalDiscount(getProductName());
        return (DtvOfferDto) getOfferDto(dtvOffer, totalDiscount);
    }

    @Override
    public String getProductName() {
        return Constants.DTV_PRODUCT_STR;
    }

    @Override
    public OfferDto createConcreteDto(Offer offer) {
        return DtvOfferDto.builder()
                .channelNumber(((DtvOffer) offer).getChannelNumber())
                .build();
    }
}

package edu.baoss.offerservice.services;

import edu.baoss.offerservice.dto.OfferDto;
import edu.baoss.offerservice.dto.TariffDto;
import edu.baoss.offerservice.exceptions.NoOfferFoundException;
import edu.baoss.offerservice.model.Discount;
import edu.baoss.offerservice.model.Offer;
import edu.baoss.offerservice.model.Tariff;
import edu.baoss.offerservice.repositories.TariffRepository;
import edu.baoss.offerservice.util.StreamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TariffService extends OfferService {
    @Autowired
    TariffRepository tariffRepository;

    public List<TariffDto> getTariffs() {
        List<? extends Offer> offers = tariffRepository.findAll();
        return StreamUtil.toStream(getOffers(offers))
                .map(TariffDto.class::cast)
                .collect(Collectors.toList());
    }

    public TariffDto getTariff(String tariffId) {
        Tariff tariff = tariffRepository.getById(tariffId).orElseThrow(NoOfferFoundException::new);
        Discount totalDiscount = discountService.getTotalDiscount(getProductName());
        return (TariffDto) getOfferDto(tariff, totalDiscount);
    }

    @Override
    public String getProductName() {
        return Constants.MOBILE_PRODUCT_STR;
    }

    @Override
    public OfferDto createConcreteDto(Offer offer) {
        Tariff tariff = (Tariff) offer;
        return TariffDto.builder()
                .tariffName(tariff.getTariffName())
                .freeMinutes(tariff.getFreeMinutes())
                .freeSms(tariff.getFreeSms())
                .internetGBs(tariff.getInternetGBs())
                .minuteOfCallPrice(tariff.getMinuteOfCallPrice())
                .oneSmsPrice(tariff.getOneSmsPrice())
                .roamingPerMinuteCallPrice(tariff.getRoamingPerMinuteCallPrice())
                .roamingPerMinuteInternetPrice(tariff.getRoamingPerMinuteInternetPrice())
                .build();
    }
}

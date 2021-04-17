package edu.baoss.offerservice.services;

import edu.baoss.offerservice.dto.IOfferDto;
import edu.baoss.offerservice.dto.TariffDto;
import edu.baoss.offerservice.model.Discount;
import edu.baoss.offerservice.model.IOffer;
import edu.baoss.offerservice.model.Tariff;
import edu.baoss.offerservice.repositories.TariffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TariffService extends OfferService {
    @Autowired
    TariffRepository tariffRepository;

    public List<TariffDto> getTariffs() {
        List<IOffer> offers = tariffRepository.findAll().stream()
                .map(offer -> (IOffer) offer)
                .collect(Collectors.toList());
        return getOffers(offers).stream()
                .map(offer -> (TariffDto) offer)
                .collect(Collectors.toList());
    }

    @Override
    public String getProductName() {
        return "Mobile product";
    }

    @Override
    public IOfferDto createDtoObject(IOffer offer, Discount discount,
                                     double discountedPrice, String priceEnding) {
        Tariff tariff = (Tariff) offer;
        return TariffDto.builder()
                .id(offer.getId())
                .tariffName(tariff.getTariff_name())
                .startingPrice(offer.getRent())
                .discountedPrice(discountedPrice)
                .priceEnding(priceEnding)
                .freeMinutes(tariff.getFreeMinutes())
                .freeSms(tariff.getFreeSms())
                .internetGBs(tariff.getInternetGBs())
                .minuteOfCallPrice(tariff.getMinuteOfCallPrice())
                .oneSmsPrice(tariff.getOneSmsPrice())
                .roamingPerMinuteCallPrice(tariff.getRoamingPerMinuteCallPrice())
                .roamingPerMinuteInternetPrice(tariff.getRoamingPerMinuteInternetPrice())
                .discount(discount.getValue())
                .discountEndDate(formatter.format(discount.getEndDate()))
                .build();
    }
}

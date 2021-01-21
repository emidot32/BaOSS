package edu.baoss.offerservice.services;

import edu.baoss.offerservice.dto.IOfferDto;
import edu.baoss.offerservice.dto.TariffDto;
import edu.baoss.offerservice.model.Discount;
import edu.baoss.offerservice.model.IOffer;
import edu.baoss.offerservice.model.Tariff;
import edu.baoss.offerservice.repositories.TariffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
    public IOfferDto createDtoObject(IOffer offer, Discount discount,
                                     double discountedPrice, String priceEnding) {
        Tariff tariff = (Tariff) offer;
        TariffDto tariffDto = TariffDto.builder()
                .id(offer.getId())
                .tariff_name(tariff.getTariff_name())
                .startingPrice((double) offer.getRent())
                .discountedPrice(discountedPrice)
                .priceEnding(priceEnding)
                .free_minutes(tariff.getFree_minutes())
                .free_sms(tariff.getFree_sms())
                .internet_GBs(tariff.getInternet_GBs())
                .minute_of_call_price(tariff.getMinute_of_call_price())
                .one_sms_price(tariff.getOne_sms_price())
                .roaming_per_minute_call_price(tariff.getRoaming_per_minute_call_price())
                .roaming_per_minute_internet_price(tariff.getRoaming_per_minute_internet_price())
                .build();
        if (discount != null) {
            tariffDto.setDiscount(discount.getValue());
            tariffDto.setDiscountEndDate(formatter.format(discount.getEnd_date()));
        } else {
            tariffDto.setDiscount(0);
            tariffDto.setDiscountEndDate("");
        }
        return tariffDto;
    }
}

package edu.baoss.offerservice.services;

import edu.baoss.offerservice.dto.IOfferDto;
import edu.baoss.offerservice.model.Discount;
import edu.baoss.offerservice.model.DtvOffer;
import edu.baoss.offerservice.model.IOffer;
import edu.baoss.offerservice.repositories.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public abstract class OfferService {
    @Autowired
    DiscountRepository discountRepository;
    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    public List<IOfferDto> getOffers(List<IOffer> offers) {
        offers.sort(Comparator.comparingInt(IOffer::getRent));
        Collections.reverse(offers);
        List<IOfferDto> offerDtos = new ArrayList<>();
        String productString = Converter.getProductNameByInstance(offers.get(0));
        List<Discount> discounts = getAppropriateDiscounts(productString);

        if (!discounts.isEmpty()) {
            Discount totalDiscount = getTotalDiscount(discounts);
            for (IOffer offer : offers) {
                double startPrice = offer.getRent();
                double discountedPrice = Math.round(startPrice * (100 - totalDiscount.getValue())) / 100.0;
                String priceEnding = Converter.getConvertedString(offer.getCurrency(), offer.getRent_time());
                offerDtos.add(createDtoObject(offer, totalDiscount, discountedPrice, priceEnding));
            }
        } else {
            for (IOffer offer : offers) {
                String priceEnding = Converter.getConvertedString(offer.getCurrency(), offer.getRent_time());
                offerDtos.add(createDtoObject(offer, null, offer.getRent(), priceEnding));
            }
        }

        return offerDtos;
    }

    public List<Discount> getAppropriateDiscounts(String productString){
        return discountRepository.findAll()
                .stream()
                .filter(discount -> {
                    Date nowDate = new Date();
                    return  (("all".equalsIgnoreCase(discount.getApplied_for()) ||
                            productString.equalsIgnoreCase(discount.getApplied_for()))
                            &&
                            ((nowDate.after(discount.getStart_date())
                                    && nowDate.before(discount.getEnd_date()))));
                })
                .collect(Collectors.toList());
    }

    public Discount getTotalDiscount(List<Discount> discounts) {
        return Discount.builder()
            .value(discounts
                .stream()
                .mapToInt(Discount::getValue)
                .sum())
            .end_date(discounts
                .stream()
                .min(Comparator.comparing(Discount::getEnd_date))
                .get().getEnd_date())
            .build();
    }

    public abstract IOfferDto createDtoObject(IOffer offer, Discount discount,
                                              double discountedPrice, String priceEnding);
}

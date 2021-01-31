package edu.baoss.offerservice.services;

import edu.baoss.offerservice.dto.IOfferDto;
import edu.baoss.offerservice.model.Discount;
import edu.baoss.offerservice.model.IOffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public abstract class OfferService {
    @Autowired
    DiscountService discountService;
    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    public List<IOfferDto> getOffers(List<IOffer> offers) {
        offers.sort(Comparator.comparingDouble(IOffer::getRent));
        Collections.reverse(offers);
        List<IOfferDto> offerDtos = new ArrayList<>();
        Discount totalDiscount = discountService.getTotalDiscount(getProductName());
        for (IOffer offer : offers) {
            double startPrice = offer.getRent();
            double discountedPrice = discountService.calcDiscountedPrice(startPrice, totalDiscount);
            String priceEnding = Converter.getConvertedString(offer.getCurrency(), offer.getRentTime());
            offerDtos.add(createDtoObject(offer, totalDiscount, discountedPrice, priceEnding));
        }

        return offerDtos;
    }

    public abstract String getProductName();

    public abstract IOfferDto createDtoObject(IOffer offer, Discount discount,
                                              double discountedPrice, String priceEnding);
}

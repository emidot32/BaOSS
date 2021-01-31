package edu.baoss.offerservice.services;

import edu.baoss.offerservice.model.Discount;
import edu.baoss.offerservice.repositories.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiscountService {
    @Autowired
    DiscountRepository discountRepository;

    public double getDiscountedPrice(double startPrice, String resourceName) {
        return calcDiscountedPrice(startPrice, getTotalDiscount(resourceName));
    }

    public List<Discount> getActiveDiscounts() {
        Date nowDate = new Date();
        return discountRepository.findAll()
                .stream()
                .filter(discount -> ((nowDate.after(discount.getStartDate())
                        && nowDate.before(discount.getEndDate()))))
                .collect(Collectors.toList());
    }

    private List<Discount> getAppropriateDiscounts(String productName){
        return discountRepository.findAll()
                .stream()
                .filter(discount -> {
                    Date nowDate = new Date();
                    return  (("All products".equalsIgnoreCase(discount.getAppliedFor()) ||
                            productName.equalsIgnoreCase(discount.getAppliedFor()))
                            &&
                            ((nowDate.after(discount.getStartDate())
                                    && nowDate.before(discount.getEndDate()))));
                })
                .collect(Collectors.toList());
    }

    public Discount getTotalDiscount(String productString) {
        List<Discount> discounts = getAppropriateDiscounts(productString);
        if (discounts.isEmpty()) {
            return Discount.builder()
                    .value(0)
                    .endDate(new Date())
                    .build();
        }
        return Discount.builder()
                .value(discounts
                        .stream()
                        .mapToInt(Discount::getValue)
                        .sum())
                .endDate(discounts
                        .stream()
                        .min(Comparator.comparing(Discount::getEndDate))
                        .get().getEndDate())
                .build();
    }

    public double calcDiscountedPrice(double startPrice, Discount totalDiscount) {
        return Math.round(startPrice * (100 - totalDiscount.getValue())) / 100.0;
    }
}

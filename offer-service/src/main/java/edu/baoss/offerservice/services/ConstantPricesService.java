package edu.baoss.offerservice.services;

import edu.baoss.offerservice.dto.ConstantPricesDto;
import edu.baoss.offerservice.model.ConstantPrices;
import edu.baoss.offerservice.model.Discount;
import edu.baoss.offerservice.repositories.ConstantPricesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConstantPricesService  {
    @Autowired
    ConstantPricesRepository constantPricesRepository;
    @Autowired
    DiscountService discountService;

    public ConstantPricesDto getConstantPrices() {
        ConstantPrices constantPrices = constantPricesRepository.findAll().get(0);
        Discount totalDiscount = discountService.getTotalDiscount(Constants.OPTIONAL_PRODUCTS_STR);
        return ConstantPricesDto.builder()
                .deliveryPrices(new double[]{constantPrices.getDeliveryPrice(),
                        discountService.calcDiscountedPrice(constantPrices.getDeliveryPrice(), totalDiscount)})
                .fixedIpPrices(new double[]{constantPrices.getFixedIpPrice(),
                        discountService.calcDiscountedPrice(constantPrices.getFixedIpPrice(), totalDiscount)})
                .installationPricesForDtvProduct(new double[]{constantPrices.getInstallationPriceForDtvProduct(),
                        discountService.calcDiscountedPrice(constantPrices.getInstallationPriceForDtvProduct(), totalDiscount)})
                .installationPricesForInternetProduct(new double[]{constantPrices.getInstallationPriceForInternetProduct(),
                        discountService.calcDiscountedPrice(constantPrices.getInstallationPriceForInternetProduct(), totalDiscount)})
                .ipv6SupportPrices(new double[]{constantPrices.getIpv6SupportPrice(),
                        discountService.calcDiscountedPrice(constantPrices.getIpv6SupportPrice(), totalDiscount)})
                .supportOf5gPrices(new double[]{constantPrices.getSupportOf5gPrice(),
                        constantPrices.getSupportOf5gPrice()})
                .cableOneMeterPrice(new double[]{constantPrices.getCableOneMeterPrice(),
                        constantPrices.getCableOneMeterPrice()})
                .discount(totalDiscount.getValue())
                .id(constantPrices.getId())
                .discountEndDate(Constants.onlyDateFormat.format(totalDiscount.getEndDate()))
                .build();
    }
}

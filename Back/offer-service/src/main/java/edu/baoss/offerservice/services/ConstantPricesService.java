package edu.baoss.offerservice.services;

import edu.baoss.offerservice.dto.ConstantPricesDto;
import edu.baoss.offerservice.dto.IOfferDto;
import edu.baoss.offerservice.model.ConstantPrices;
import edu.baoss.offerservice.model.Discount;
import edu.baoss.offerservice.model.IOffer;
import edu.baoss.offerservice.repositories.ConstantPricesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConstantPricesService extends OfferService {
    @Autowired
    ConstantPricesRepository constantPricesRepository;
    @Autowired
    DiscountService discountService;

    public ConstantPricesDto getConstantPrices() {
        ConstantPrices constantPrices = constantPricesRepository.findAll().get(0);
        Discount totalDiscount = discountService.getTotalDiscount(getProductName());
        return ConstantPricesDto.builder()
                .deliveryPrices(new double[]{constantPrices.getDeliveryPrice(),
                        discountService.calcDiscountedPrice(constantPrices.getDeliveryPrice(), totalDiscount)})
                .fixedIpPrices(new double[]{constantPrices.getFixed_ip_price(),
                        discountService.calcDiscountedPrice(constantPrices.getFixed_ip_price(), totalDiscount)})
                .installationPricesForDtvProduct(new double[]{constantPrices.getInstallationPriceForDtvProduct(),
                        discountService.calcDiscountedPrice(constantPrices.getInstallationPriceForDtvProduct(), totalDiscount)})
                .installationPricesForInternetProduct(new double[]{constantPrices.getInstallationPriceForInternetProduct(),
                        discountService.calcDiscountedPrice(constantPrices.getInstallationPriceForInternetProduct(), totalDiscount)})
                .ipv6SupportPrices(new double[]{constantPrices.getIpv6_support_price(),
                        discountService.calcDiscountedPrice(constantPrices.getIpv6_support_price(), totalDiscount)})
                .supportOf5gPrices(new double[]{constantPrices.getSupportOf5gPrice(),
                        constantPrices.getSupportOf5gPrice()})
                .cableOneMeterPrice(new double[]{constantPrices.getCableOneMeterPrice(),
                        constantPrices.getCableOneMeterPrice()})
                .discount(totalDiscount.getValue())
                .id(constantPrices.getId())
                .discountEndDate(formatter.format(totalDiscount.getEndDate()))
                .build();
    }

    @Override
    public String getProductName() {
        return "Optional products and installation";
    }

    @Override
    public IOfferDto createDtoObject(IOffer offer, Discount discount, double discountedPrice, String priceEnding) {
        return null;
    }
}

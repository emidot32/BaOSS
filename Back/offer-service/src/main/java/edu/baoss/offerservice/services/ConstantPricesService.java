package edu.baoss.offerservice.services;

import edu.baoss.offerservice.dto.ConstantPricesDto;
import edu.baoss.offerservice.dto.IOfferDto;
import edu.baoss.offerservice.model.ConstantPrices;
import edu.baoss.offerservice.model.Discount;
import edu.baoss.offerservice.model.IOffer;
import edu.baoss.offerservice.repositories.ConstantPricesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConstantPricesService extends OfferService {
    @Autowired
    ConstantPricesRepository constantPricesRepository;

    public ConstantPricesDto getConstantPrices() {
        ConstantPrices constantPrices = constantPricesRepository.findAll().get(0);
        List<Discount> discounts = getAppropriateDiscounts("constant_prices");
        if (!discounts.isEmpty()) {
            Discount totalDiscount = getTotalDiscount(discounts);
            return ConstantPricesDto.builder()
                    .delivery_prices(new double[]{constantPrices.getDelivery_price(),
                            calcDiscountedPrice(constantPrices.getDelivery_price(), totalDiscount)})
                    .fixed_ip_prices(new double[]{constantPrices.getFixed_ip_price(),
                            calcDiscountedPrice(constantPrices.getFixed_ip_price(), totalDiscount)})
                    .installation_prices_for_dtv_product(new double[]{constantPrices.getInstallation_price_for_dtv_product(),
                            calcDiscountedPrice(constantPrices.getInstallation_price_for_dtv_product(), totalDiscount)})
                    .installation_prices_for_internet_product(new double[]{constantPrices.getInstallation_price_for_internet_product(),
                            calcDiscountedPrice(constantPrices.getInstallation_price_for_internet_product(), totalDiscount)})
                    .ipv6_support_prices(new double[]{constantPrices.getIpv6_support_price(),
                            calcDiscountedPrice(constantPrices.getIpv6_support_price(), totalDiscount)})
                    .support_of_5g_prices(new double[]{constantPrices.getSupport_of_5g_price(),
                            calcDiscountedPrice(constantPrices.getSupport_of_5g_price(), totalDiscount)})
                    .discount(totalDiscount.getValue())
                    .id(constantPrices.getId())
                    .discountEndDate(formatter.format(totalDiscount.getEnd_date()))
                    .build();
        } else {
            return ConstantPricesDto.builder()
                    .delivery_prices(new double[]{constantPrices.getDelivery_price(), constantPrices.getDelivery_price()})
                    .fixed_ip_prices(new double[]{constantPrices.getFixed_ip_price(), constantPrices.getFixed_ip_price()})
                    .support_of_5g_prices(new double[]{constantPrices.getSupport_of_5g_price(), constantPrices.getSupport_of_5g_price()})
                    .ipv6_support_prices(new double[]{constantPrices.getIpv6_support_price(), constantPrices.getIpv6_support_price()})
                    .installation_prices_for_internet_product(new double[]{constantPrices.getInstallation_price_for_internet_product(),
                            constantPrices.getInstallation_price_for_internet_product()})
                    .installation_prices_for_dtv_product(new double[]{constantPrices.getInstallation_price_for_dtv_product(),
                            constantPrices.getInstallation_price_for_dtv_product()})
                    .discount(0)
                    .id(constantPrices.getId())
                    .discountEndDate("")
                    .build();
        }
    }

    private double calcDiscountedPrice(int startPrice, Discount totalDiscount) {
        return Math.round(startPrice * (100 - totalDiscount.getValue())) / 100.0;
    }

    @Override
    public IOfferDto createDtoObject(IOffer offer, Discount discount, double discountedPrice, String priceEnding) {
        return null;
    }
}

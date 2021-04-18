package edu.baoss.orderservice.actions.checkers;

import edu.baoss.orderservice.dtos.OrderValue;
import edu.baoss.orderservice.exceptions.CablePriceDiscrepancyException;
import edu.baoss.orderservice.exceptions.MrcPriceDiscrepancyException;
import edu.baoss.orderservice.exceptions.NrcPriceDiscrepancyException;

import static edu.baoss.orderservice.services.Constants.*;

public class PriceCheckAction implements CheckAction{
    @Override
    public void check(OrderValue orderValue) {
        double totalMRC = 0.0;
        double totalNRC = 0.0;
        for (String product: orderValue.getSelectedProducts()) {
            if (MOBILE_PRODUCT_STR.equals(product)) {
                totalMRC += orderValue.getSelectedTariff().getDiscountedPrice();
                totalNRC += orderValue.getSelectedPhoneNumber().getPrice();
                if (orderValue.isSupport5g())
                    totalNRC += orderValue.getConstantPrices().getSupportOf5gPrices()[1];
            }
            if (INTERNET_PRODUCT_STR.equals(product)) {
                totalMRC += orderValue.getSelectedSpeed().getDiscountedPrice();
                if (orderValue.isFixedIpSupport())
                    totalMRC += orderValue.getConstantPrices().getFixedIpPrices()[1];
                totalNRC += orderValue.getSelectedDevice().getPrice();
                double cablePrice = (orderValue.getCableLength() - 3) * orderValue.getConstantPrices().getCableOneMeterPrice()[1];
                if (Math.round(cablePrice) != Math.round(orderValue.getCablePriceTotal()))
                    throw new CablePriceDiscrepancyException("Cable price is calculated incorrectly!");
                else
                    totalNRC += cablePrice;
            }
            if (DTV_PRODUCT_STR.equals(product)) {
                totalMRC += orderValue.getSelectedChannelNumber().getDiscountedPrice();
            }
        }
        totalNRC += orderValue.getDeliveryPrice();
        if (Math.round(totalMRC) != Math.round(orderValue.getTotalMRC()))
            throw new MrcPriceDiscrepancyException("Total MRC price does not match the selected products!");
        if (Math.round(totalNRC) != Math.round(orderValue.getTotalNRC()))
            throw new NrcPriceDiscrepancyException("Total NRC price does not match the selected products!");
    }
}

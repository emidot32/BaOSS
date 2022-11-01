package edu.baoss.orderservice.actions.checking;

import edu.baoss.orderservice.model.ActionContext;
import edu.baoss.orderservice.model.dtos.OrderValue;
import edu.baoss.orderservice.exceptions.CablePriceDiscrepancyException;
import edu.baoss.orderservice.exceptions.MrcPriceDiscrepancyException;
import edu.baoss.orderservice.exceptions.NrcPriceDiscrepancyException;

import static edu.baoss.orderservice.Constants.*;

public class PriceCheckAction extends FeasibilityCheckAction {
    @Override
    public void check(ActionContext context) {
        double totalMRC = 0.0;
        double totalNRC = 0.0;
        for (String product: context.getOrderValue().getSelectedProducts()) {
            if (MOBILE_PRODUCT_STR.equals(product)) {
                totalMRC += context.getOrderValue().getSelectedTariff().getDiscountedPrice();
                totalNRC += context.getOrderValue().getSelectedPhoneNumber().getPrice();
                if (context.getOrderValue().isSupport5g())
                    totalNRC += context.getOrderValue().getConstantPrices().getSupportOf5gPrices()[1];
            }
            if (INTERNET_PRODUCT_STR.equals(product)) {
                totalMRC += context.getOrderValue().getSelectedSpeed().getDiscountedPrice();
                if (context.getOrderValue().isFixedIpSupport())
                    totalMRC += context.getOrderValue().getConstantPrices().getFixedIpPrices()[1];
                if (context.getOrderValue().getSelectedDevice() != null)
                    totalNRC += context.getOrderValue().getSelectedDevice().getPrice();
                double cablePrice = (context.getOrderValue().getCableLength() - 3) * context.getOrderValue().getConstantPrices().getCableOneMeterPrice()[1];
                if (Math.round(cablePrice) != Math.round(context.getOrderValue().getCablePriceTotal()))
                    throw new CablePriceDiscrepancyException("Cable price is calculated incorrectly!");
                else
                    totalNRC += cablePrice;
            }
            if (DTV_PRODUCT_STR.equals(product)) {
                totalMRC += context.getOrderValue().getSelectedChannelNumber().getDiscountedPrice();
            }
        }
        totalNRC += context.getOrderValue().getDeliveryPrice();
        if (Math.round(totalMRC) != Math.round(context.getOrderValue().getTotalMRC()))
            throw new MrcPriceDiscrepancyException("Total MRC price does not match the selected products!");
        if (Math.round(totalNRC) != Math.round(context.getOrderValue().getTotalNRC()))
            throw new NrcPriceDiscrepancyException("Total NRC price does not match the selected products!");
    }
}

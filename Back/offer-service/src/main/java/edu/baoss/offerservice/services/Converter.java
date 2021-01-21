package edu.baoss.offerservice.services;

import edu.baoss.offerservice.model.DtvOffer;
import edu.baoss.offerservice.model.IOffer;
import edu.baoss.offerservice.model.InternetOffer;

import java.util.HashMap;
import java.util.Map;

public class Converter {
    private final static Map<String, String> currencyTable = new HashMap<>();
    private final static Map<Integer, String> timeTable = new HashMap<>();
    static {
        currencyTable.put("dollar", "$");
        currencyTable.put("hryvnia", "₴");
        currencyTable.put("euro", "€");

        timeTable.put(1, "day");
        timeTable.put(30, "month");
        timeTable.put(31, "month");
        timeTable.put(29, "month");
        timeTable.put(365, "year");
    }

    public static String getConvertedString(String currency, int rentTime){
        return String.format(" %s/%s", currencyTable.get(currency), timeTable.get(rentTime));
    }

    public static String getProductNameByInstance(IOffer offer){
        if (offer instanceof InternetOffer) return "internet";
        else if (offer instanceof DtvOffer) return "dtv";
        else return "mobile";
    }
}

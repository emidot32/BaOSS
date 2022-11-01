package edu.baoss.offerservice.services;

import java.util.HashMap;
import java.util.Map;

public class Converter {
    public final static Map<String, String> CURRENCY_TABLE = Map.of(
            "dollar", "$",
            "hryvnia", "₴",
            "euro", "€");
    public final static Map<Integer, String> RENT_TIME_TABLE = Map.of(
            1, "day",
            30, "month",
            365, "year");

    public static String getConvertedString(String currency, int rentTime) {
        return String.format(" %s/%s", CURRENCY_TABLE.get(currency), RENT_TIME_TABLE.get(rentTime));
    }
}

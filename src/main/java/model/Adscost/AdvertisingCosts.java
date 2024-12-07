package model.Adscost;

import java.util.*;

public class AdvertisingCosts {
    private static Map<String, Map<String, Integer>> advertisingCosts = new HashMap<>();

    public static void initializeCosts() {
        advertisingCosts.put("Television", Map.of(
            "Students", 1414001,
            "Professionals", 1663397,
            "Seniors", 1167187
        ));
        advertisingCosts.put("Radio", Map.of(
            "Students", 1072266,
            "Professionals", 1040847,
            "Seniors", 626880
        ));
        advertisingCosts.put("Newspaper", Map.of(
            "Students", 509258,
            "Professionals", 1805058,
            "Seniors", 821457
        ));
        advertisingCosts.put("Internet", Map.of(
            "Students", 688083,
            "Professionals", 587244,
            "Seniors", 707078
        ));
    }

    public static int getCost(String channel, String market) {
        return advertisingCosts.getOrDefault(channel, Collections.emptyMap()).getOrDefault(market, 0);
    }

    public static Map<String, Map<String, Integer>> getAllCosts() {
        return advertisingCosts;
    }
}


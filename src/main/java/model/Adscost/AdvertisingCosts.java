package model.Adscost;

import java.util.*;

public class AdvertisingCosts {
    private static Map<String, Map<String, Integer>> advertisingCosts = new HashMap<>();

    public static void initializeCosts() {
        advertisingCosts.put("Channel 1", Map.of(
            "Market 1", 1414001,
            "Market 2", 1663397,
            "Market 3", 1167187
        ));
        advertisingCosts.put("Channel 2", Map.of(
            "Market 1", 1072266,
            "Market 2", 1040847,
            "Market 3", 626880
        ));
        advertisingCosts.put("Channel 3", Map.of(
            "Market 1", 509258,
            "Market 2", 1805058,
            "Market 3", 821457
        ));
        advertisingCosts.put("Channel 4", Map.of(
            "Market 1", 688083,
            "Market 2", 587244,
            "Market 3", 707078
        ));
    }

    public static int getCost(String channel, String market) {
        return advertisingCosts.getOrDefault(channel, Collections.emptyMap()).getOrDefault(market, 0);
    }

    public static Map<String, Map<String, Integer>> getAllCosts() {
        return advertisingCosts;
    }
}


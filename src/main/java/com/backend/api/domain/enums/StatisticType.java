package com.backend.api.domain.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum StatisticType {
    TOTAL_PRODUCTS, MOST_EXPENSIVE, CHEAPEST_PRODUCT;

    public static StatisticType get(String name) {
        return Arrays.stream(StatisticType.values())
                .filter(e -> e.name().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
}

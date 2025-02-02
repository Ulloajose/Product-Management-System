package com.backend.api.domain.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Category {
    ELECTRONICS, CLOTHING, FOOD, HEALTH, HOME, AUTOMOTIVE, BOOKS, MUSIC, GAMES, OTHER;

    public static Category get(String name) {
        return Arrays.stream(Category.values())
                .filter(e -> e.name().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
}

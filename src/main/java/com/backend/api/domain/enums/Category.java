package com.backend.api.domain.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Category {
    ELECTRONICS(1), CLOTHING(2), FOOD(3), HEALTH(4),
    HOME(5), AUTOMOTIVE(6), BOOKS(7), MUSIC(8), GAMES(9), OTHER(10);

    Category(int id) {
        this.id = id;
    }

    private final int id;

    public static Category get(String name) {
        return Arrays.stream(Category.values())
                .filter(e -> e.name().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
}

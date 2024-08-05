package com.backend.api.domain.dto;

import com.backend.api.domain.enums.Category;
import com.backend.api.domain.enums.StatisticType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class StatisticDto {

    private Long id;
    private StatisticType type;
    private Category category;
    private BigDecimal value;
}

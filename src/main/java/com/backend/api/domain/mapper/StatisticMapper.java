package com.backend.api.domain.mapper;

import com.backend.api.domain.dto.StatisticDto;
import com.backend.api.domain.enums.Category;
import com.backend.api.domain.enums.StatisticType;
import com.backend.api.infrastructure.entity.CategoryEntity;
import com.backend.api.infrastructure.entity.StatisticEntity;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.util.Objects;

@UtilityClass
public class StatisticMapper {

    public StatisticDto map(StatisticEntity statisticEntity){
        if (Objects.isNull(statisticEntity)) return null;
        return StatisticDto.builder()
                .id(statisticEntity.getId())
                .value(statisticEntity.getValue())
                .type(StatisticType.get(statisticEntity.getType()))
                .category(Category.get(statisticEntity.getCategory().getName()))
                .build();
    }

    public StatisticEntity map(StatisticDto statisticDto, CategoryEntity category, Long userId){
        if (Objects.isNull(statisticDto)) return null;
        return StatisticEntity.builder()
                .id(statisticDto.getId())
                .type(statisticDto.getType().name())
                .value(statisticDto.getValue())
                .category(category)
                .lastModifiedDate(LocalDateTime.now())
                .lastModifiedBy(userId)
                .build();
    }
}

package com.backend.api.domain.port.out;

import com.backend.api.domain.dto.StatisticDto;
import com.backend.api.domain.enums.Category;
import com.backend.api.domain.enums.StatisticType;

import java.util.List;

public interface StatisticRepositoryPort {
    void save(StatisticDto statisticDto, Long userId);
    StatisticDto find(StatisticType type, Category category);
    List<StatisticDto> findAll();
}

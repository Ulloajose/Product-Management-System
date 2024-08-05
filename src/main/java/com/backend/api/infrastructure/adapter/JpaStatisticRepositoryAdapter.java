package com.backend.api.infrastructure.adapter;

import com.backend.api.domain.dto.StatisticDto;
import com.backend.api.domain.enums.Category;
import com.backend.api.domain.enums.StatisticType;
import com.backend.api.domain.mapper.StatisticMapper;
import com.backend.api.domain.port.out.StatisticRepositoryPort;
import com.backend.api.infrastructure.entity.CategoryEntity;
import com.backend.api.infrastructure.entity.StatisticEntity;
import com.backend.api.infrastructure.repository.CategoryRepository;
import com.backend.api.infrastructure.repository.StatisticRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class JpaStatisticRepositoryAdapter implements StatisticRepositoryPort {

    private final StatisticRepository statisticRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public void save(StatisticDto statisticDto, Long userId) {
        CategoryEntity category = null;
        Category dtoCategory = statisticDto.getCategory();
        if (Objects.nonNull(dtoCategory)){
            category = categoryRepository.findByName(dtoCategory.name()).orElse(null);
        }
        StatisticEntity statisticEntity = StatisticMapper.map(statisticDto, category, userId);
        statisticRepository.save(statisticEntity);
    }

    @Override
    public StatisticDto find(StatisticType type, Category category) {
        StatisticEntity statisticEntity = statisticRepository
                .findByTypeAndCategoryName(type.name(), category.name())
                .orElse(null);
        return StatisticMapper.map(statisticEntity);
    }

    @Override
    public List<StatisticDto> findAll() {
        return statisticRepository.findAll().stream().map(StatisticMapper::map).toList();
    }
}

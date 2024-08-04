package com.backend.api.domain.mapper;

import com.backend.api.domain.dto.ProductDto;
import com.backend.api.domain.enums.Category;
import com.backend.api.domain.util.DateUtil;
import com.backend.api.infrastructure.entity.CategoryEntity;
import com.backend.api.infrastructure.entity.ProductEntity;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@UtilityClass
public class ProductMapper {

    public ProductEntity map(ProductDto productDto, Set<CategoryEntity> categories){
        if (Objects.isNull(productDto)) return null;

        return ProductEntity.builder()
                .lastModifiedDate(LocalDateTime.now())
                .name(productDto.getName())
                .cost(productDto.getCost())
                .price(productDto.getPrice())
                .categories(categories)
                .description(productDto.getDescription())
                .build();
    }

    public ProductDto map(ProductEntity productEntity){
        if (Objects.isNull(productEntity)) return null;

        return ProductDto.builder()
                .id(productEntity.getId())
                .createDate(DateUtil.convertLocalDateTimeToString(productEntity.getCreatedDate()))
                .name(productEntity.getName())
                .cost(productEntity.getCost())
                .price(productEntity.getPrice())
                .categories(mapCategory(productEntity.getCategories()))
                .description(productEntity.getDescription())
                .build();
    }

    private List<Category> mapCategory(Set<CategoryEntity> categories){
        return categories.stream()
                .map(category -> Category.get(category.getName()))
                .toList();
    }
}

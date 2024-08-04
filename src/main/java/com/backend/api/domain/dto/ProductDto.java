package com.backend.api.domain.dto;

import com.backend.api.domain.enums.Category;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class ProductDto {

    private Long id;
    private BigDecimal price;
    private BigDecimal cost;
    private String description;
    private String name;
    private List<Category> categories;
    private String createDate;
}

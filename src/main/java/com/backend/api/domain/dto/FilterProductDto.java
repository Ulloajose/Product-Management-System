package com.backend.api.domain.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Month;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class FilterProductDto {

    private Long id;
    private String productName;
    private String category;
    private Month month;
}

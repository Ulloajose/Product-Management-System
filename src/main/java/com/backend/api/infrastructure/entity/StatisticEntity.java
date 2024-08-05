package com.backend.api.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "system_statistic")
public class StatisticEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private BigDecimal value;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private CategoryEntity category;
}

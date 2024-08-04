package com.backend.api.infrastructure.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@SuperBuilder
@Table(name = "product")
public class ProductEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal price;
    private BigDecimal cost;
    private String description;
    private String name;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name="product_category", joinColumns= @JoinColumn(name="product_id"),
            inverseJoinColumns=@JoinColumn(name="category_id"),
            uniqueConstraints= {@UniqueConstraint(columnNames= {"product_id", "category_id"})})
    private Set<CategoryEntity> categories;
}

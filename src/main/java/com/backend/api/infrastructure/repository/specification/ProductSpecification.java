package com.backend.api.infrastructure.repository.specification;

import com.backend.api.infrastructure.entity.CategoryEntity;
import com.backend.api.infrastructure.entity.ProductEntity;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

import java.time.Month;
import java.util.Objects;

@UtilityClass
public class ProductSpecification {

    public Specification<ProductEntity> notDeleted() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isFalse(root.get("deleted"));
    }

    public Specification<ProductEntity> productId(Long id) {
        return (root, query, criteriaBuilder) -> {
            if (Objects.nonNull(id)) {
                return criteriaBuilder.equal(root.get("id"), id);
            } else {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
        };
    }

    public Specification<ProductEntity> productNameLike(String name) {
        return (root, query, criteriaBuilder) -> {
            if (Objects.nonNull(name) && !name.isEmpty()) {
                return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
            } else {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
        };
    }

    public Specification<ProductEntity> categoryNameLike(String category) {
        return (root, query, criteriaBuilder) -> {
            if (Objects.nonNull(category) && !category.isEmpty()) {
                Join<ProductEntity, CategoryEntity> categoryEntityJoin = root.join("categories");
                return criteriaBuilder.like(
                        criteriaBuilder.lower(categoryEntityJoin.get("name")), "%" + category.toLowerCase() + "%");
            } else {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
        };
    }

    public Specification<ProductEntity> byMonth(Month month) {
        return (root, query, criteriaBuilder) -> {
            if (Objects.nonNull(month)) {
                Expression<String> monthExpression = criteriaBuilder.function(
                        "TO_CHAR", String.class, root.get("createdDate"), criteriaBuilder.literal("MONTH"));
                return criteriaBuilder.like(monthExpression, "%" + month.name() + "%");
            } else {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
        };
    }
}

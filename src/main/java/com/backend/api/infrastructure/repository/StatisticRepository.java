package com.backend.api.infrastructure.repository;

import com.backend.api.infrastructure.entity.StatisticEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatisticRepository extends JpaRepository<StatisticEntity, Long> {
    Optional<StatisticEntity> findByTypeAndCategoryName(String type, String categoryName);
}

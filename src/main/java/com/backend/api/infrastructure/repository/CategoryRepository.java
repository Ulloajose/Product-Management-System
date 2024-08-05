package com.backend.api.infrastructure.repository;

import com.backend.api.infrastructure.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
    Set<CategoryEntity> findByNameIn(List<String> names);
    Optional<CategoryEntity> findByName(String name);
}

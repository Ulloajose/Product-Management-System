package com.backend.api.infrastructure.adapter;

import com.backend.api.domain.dto.FilterProductDto;
import com.backend.api.domain.dto.ProductDto;
import com.backend.api.domain.exeption.NotFoundException;
import com.backend.api.domain.model.DataTablesResponse;
import com.backend.api.infrastructure.entity.ProductEntity;
import com.backend.api.infrastructure.repository.ProductRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Disabled
@ContextConfiguration(classes = {JpaProductRepositoryAdapter.class})
@ExtendWith(SpringExtension.class)
class JpaProductRepositoryAdapterTest {
    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private JpaProductRepositoryAdapter jpaProductRepositoryAdapter;

    @Test
    void testDeleteProduct() {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setCategories(new HashSet<>());
        productEntity.setName("JaneDoe");
        productEntity.setCreatedBy(1L);
        productEntity.setCreatedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        productEntity.setDeleted(true);
        productEntity.setId(1L);
        productEntity.setLastModifiedBy(1L);
        productEntity.setLastModifiedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        productEntity.setCost(BigDecimal.ONE);
        productEntity.setPrice(BigDecimal.TEN);
        Optional<ProductEntity> ofResult = Optional.of(productEntity);

        when(productRepository.save(Mockito.any())).thenReturn(productEntity);
        when(productRepository.findById(Mockito.any())).thenReturn(ofResult);

        jpaProductRepositoryAdapter.deleteProduct(42L, 1L);

        verify(productRepository).findById(42L);
        verify(productRepository).save(isA(ProductEntity.class));
    }


    @Test
    void testDeleteProductNotFound() {
        Optional<ProductEntity> emptyResult = Optional.empty();
        when(productRepository.findById(Mockito.any())).thenReturn(emptyResult);

        assertThrows(NotFoundException.class, () -> jpaProductRepositoryAdapter.deleteProduct(42L, 1L));
        verify(productRepository).findById(42L);
    }

    @Test
    void testFindAll() {
        when(productRepository.findAll(Mockito.<Specification<ProductEntity>>any(), Mockito.any()))
                .thenReturn(new PageImpl<>(new ArrayList<>()));

        DataTablesResponse<ProductDto> actualFindAllResult = jpaProductRepositoryAdapter.findAll(1, 3,
                new String[]{"Sort", "com.backend.api.infrastructure.adapter.JpaArticleRepositoryAdapter"},
                new FilterProductDto(1L,"JaneDoe", "Tag", Month.JANUARY));

        verify(productRepository).findAll(isA(Specification.class), isA(Pageable.class));
        assertEquals(0, actualFindAllResult.getCurrentPage());
        assertEquals(0L, actualFindAllResult.getTotalItems());
        assertEquals(1, actualFindAllResult.getTotalPages());
        assertTrue(actualFindAllResult.getData().isEmpty());
    }

    @Test
    void testFindAllNotFound() {
        when(productRepository.findAll(Mockito.<Specification<ProductEntity>>any(), Mockito.any()))
                .thenThrow(new NotFoundException("An error occurred"));

        assertThrows(NotFoundException.class,
                () -> jpaProductRepositoryAdapter.findAll(1, 3,
                        new String[]{"Sort", "com.backend.api.infrastructure.adapter.JpaProductRepositoryAdapter"},
                        new FilterProductDto(1L,"JaneDoe", "Tag", Month.JANUARY)));
        verify(productRepository).findAll(isA(Specification.class), isA(Pageable.class));
    }
}

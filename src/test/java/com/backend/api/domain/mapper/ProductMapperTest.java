package com.backend.api.domain.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.backend.api.domain.dto.ProductDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

import com.backend.api.infrastructure.entity.ProductEntity;
import org.junit.jupiter.api.Test;

class ProductMapperTest {

    @Test
    void testMapProductNull() {
        ProductDto productDto = null;
        ProductEntity actualMapResult = ProductMapper.map(productDto, new HashSet<>());
        assertNull(actualMapResult);
    }


    @Test
    void testMap() {
        ProductDto productDto = new ProductDto();
        productDto.setCategories(new ArrayList<>());
        ProductEntity actualMapResult = ProductMapper.map(productDto, new HashSet<>());

        assertNull(actualMapResult.getId());
        assertNull(actualMapResult.getCreatedBy());
        assertNull(actualMapResult.getLastModifiedBy());
        assertNull(actualMapResult.getCost());
        assertNull(actualMapResult.getPrice());
        assertNull(actualMapResult.getName());
        assertFalse(actualMapResult.isDeleted());
        assertTrue(actualMapResult.getCategories().isEmpty());
    }


    @Test
    void testMapProductEntity() {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setCategories(new HashSet<>());
        productEntity.setName("JaneDoe");
        productEntity.setCreatedBy(1L);
        productEntity.setCreatedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        productEntity.setDeleted(true);
        productEntity.setId(1L);
        productEntity.setLastModifiedBy(1L);
        productEntity.setLastModifiedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        productEntity.setPrice(BigDecimal.ZERO);
        productEntity.setCost(BigDecimal.ZERO);

        ProductDto actualMapResult = ProductMapper.map(productEntity);

        assertEquals("1970-01-01T00:00:00Z", actualMapResult.getCreateDate());
        assertEquals(BigDecimal.ZERO, actualMapResult.getPrice());
        assertEquals(BigDecimal.ZERO, actualMapResult.getCost());
        assertEquals("JaneDoe", actualMapResult.getName());
        assertTrue(actualMapResult.getCategories().isEmpty());
    }
}

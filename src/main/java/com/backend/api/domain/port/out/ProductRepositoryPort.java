package com.backend.api.domain.port.out;

import com.backend.api.domain.dto.FilterProductDto;
import com.backend.api.domain.dto.ProductDto;
import com.backend.api.domain.model.DataTablesResponse;

public interface ProductRepositoryPort {
    void deleteProduct(Long productId, Long userId);
    DataTablesResponse<ProductDto> findAll(
            int pageNumber, int pageSize, String[] sort, FilterProductDto filterProductDto);
    void insertProduct(ProductDto productDto, Long userId);
    void updateProduct(ProductDto productDto, Long userId, Long productId);
    ProductDto findProductById(Long productId);
}

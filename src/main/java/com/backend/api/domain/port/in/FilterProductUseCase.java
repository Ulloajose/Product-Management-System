package com.backend.api.domain.port.in;

import com.backend.api.domain.dto.FilterProductDto;
import com.backend.api.domain.dto.ProductDto;
import com.backend.api.domain.model.DataTablesResponse;

public interface FilterProductUseCase {

    DataTablesResponse<ProductDto> findAll(
            int pageNumber, int pageSize, String[] sort, FilterProductDto filterProductDto);
    ProductDto findById(Long productId);
}

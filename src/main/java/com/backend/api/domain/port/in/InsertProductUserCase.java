package com.backend.api.domain.port.in;

import com.backend.api.domain.dto.ProductDto;

public interface InsertProductUserCase {
    void insertProduct(ProductDto productDto);
}

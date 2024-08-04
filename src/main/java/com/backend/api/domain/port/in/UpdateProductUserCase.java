package com.backend.api.domain.port.in;

import com.backend.api.domain.dto.ProductDto;

public interface UpdateProductUserCase {
    void updateProduct(ProductDto productDto, Long productId);
}

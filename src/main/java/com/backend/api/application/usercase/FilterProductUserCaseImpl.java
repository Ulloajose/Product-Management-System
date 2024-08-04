package com.backend.api.application.usercase;

import com.backend.api.domain.dto.FilterProductDto;
import com.backend.api.domain.dto.ProductDto;
import com.backend.api.domain.model.DataTablesResponse;
import com.backend.api.domain.port.in.FilterProductUseCase;
import com.backend.api.domain.port.out.ProductRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FilterProductUserCaseImpl implements FilterProductUseCase {

    private final ProductRepositoryPort productRepositoryPort;

    @Override
    public DataTablesResponse<ProductDto> findAll(int pageNumber, int pageSize, String[] sort, FilterProductDto filterProductDto) {
        return productRepositoryPort.findAll(pageNumber, pageSize, sort, filterProductDto);
    }

    @Override
    public ProductDto findById(Long productId) {
        return productRepositoryPort.findProductById(productId);
    }
}

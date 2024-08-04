package com.backend.api.application.service;

import com.backend.api.domain.dto.FilterProductDto;
import com.backend.api.domain.dto.ProductDto;
import com.backend.api.domain.mapper.GenericResponseMapper;
import com.backend.api.domain.model.DataTablesResponse;
import com.backend.api.domain.model.GenericResponse;
import com.backend.api.domain.port.in.DeleteProductUserCase;
import com.backend.api.domain.port.in.FilterProductUseCase;
import com.backend.api.domain.port.in.InsertProductUserCase;
import com.backend.api.domain.port.in.UpdateProductUserCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static com.backend.api.domain.util.Constant.*;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final FilterProductUseCase filterProductUseCase;
    private final DeleteProductUserCase deleteProductUserCase;
    private final InsertProductUserCase insertProductUserCase;
    private final UpdateProductUserCase updateProductUserCase;

    public GenericResponse<DataTablesResponse<ProductDto>> findAll(
            int pageNumber, int pageSize, String[] sort, FilterProductDto filterProductDto){

        DataTablesResponse<ProductDto> response = filterProductUseCase
                .findAll(pageNumber, pageSize, sort, filterProductDto);
        return GenericResponseMapper.buildGenericResponse(response, HttpStatus.OK, HttpStatus.OK.toString());
    }

    public GenericResponse<Void> delete(Long productId){
        deleteProductUserCase.deleteProduct(productId);
        return GenericResponseMapper.buildGenericResponse(HttpStatus.OK, PRODUCT_DELETED_SUCCESS);
    }

    public GenericResponse<ProductDto> findById(Long productId){
        ProductDto productDto = filterProductUseCase.findById(productId);
        return GenericResponseMapper.buildGenericResponse(productDto, HttpStatus.OK, HttpStatus.OK.toString());
    }

    public GenericResponse<Void> insert(ProductDto productDto){
        insertProductUserCase.insertProduct(productDto);
        return GenericResponseMapper.buildGenericResponse(HttpStatus.OK, HttpStatus.OK.toString());
    }

    public GenericResponse<Void> update(ProductDto productDto, Long productId){
        updateProductUserCase.updateProduct(productDto, productId);
        return GenericResponseMapper.buildGenericResponse(HttpStatus.OK, HttpStatus.OK.toString());
    }
}

package com.backend.api.application.service;

import com.backend.api.domain.dto.FilterProductDto;
import com.backend.api.domain.dto.ProductDto;
import com.backend.api.domain.model.DataTablesResponse;
import com.backend.api.domain.model.DetailResponse;
import com.backend.api.domain.model.GenericResponse;
import com.backend.api.domain.model.ResultResponse;
import com.backend.api.domain.port.in.DeleteProductUserCase;
import com.backend.api.domain.port.in.FilterProductUseCase;
import com.backend.api.domain.port.in.InsertProductUserCase;
import com.backend.api.domain.port.in.UpdateProductUserCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ProductServiceTest {
    @MockBean
    private DeleteProductUserCase deleteProductUserCase;

    @MockBean
    private FilterProductUseCase filterProductUseCase;

    @MockBean
    private InsertProductUserCase insertProductUserCase;

    @Autowired
    private ProductService productService;

    @MockBean
    private UpdateProductUserCase updateProductUserCase;

    @Test
    void testFindAll() {
        FilterProductDto filterProductDto = new FilterProductDto();
        DataTablesResponse<ProductDto> dataTablesResponse = new DataTablesResponse<>();
        dataTablesResponse.setCurrentPage(2);
        dataTablesResponse.setTotalItems(2);
        dataTablesResponse.setTotalPages(2);
        dataTablesResponse.setData(new ArrayList<>());
        when(filterProductUseCase.findAll(anyInt(), anyInt(), any(), any())).thenReturn(dataTablesResponse);

        GenericResponse<DataTablesResponse<ProductDto>> response = productService.findAll(1, 10, new String[]{}, filterProductDto);

        DataTablesResponse<ProductDto> data = response.getData();
        assertEquals(2, data.getCurrentPage());
        assertEquals(2, data.getTotalItems());
        assertEquals(2, data.getTotalPages());
        assertTrue(data.getData().isEmpty());
        verify(filterProductUseCase, times(1)).findAll(anyInt(), anyInt(), any(), any());
    }

    @Test
    void testDelete() {
        doNothing().when(deleteProductUserCase).deleteProduct(Mockito.<Long>any());
        long productId = 1L;

        GenericResponse<Void> actualDeleteResult = productService.delete(productId);

        verify(deleteProductUserCase).deleteProduct(1L);
        ResultResponse result = actualDeleteResult.getResult();
        List<DetailResponse> details = result.getDetails();
        assertEquals(1, details.size());
        DetailResponse getResult = details.getFirst();
        assertEquals("200 OK", getResult.getMessage());
        assertEquals("200", getResult.getInternalCode());
        assertEquals("Internal component details", result.getSource());
        assertEquals("The product have been deleted successfully!", getResult.getDetail());
        assertNull(actualDeleteResult.getData());
    }

    @Test
    void testFindById() {
        ProductDto productDto = new ProductDto();
        productDto.setCategories(new ArrayList<>());
        productDto.setCost(new BigDecimal("2.3"));
        productDto.setCreateDate("2020-03-01");
        productDto.setDescription("The characteristics of someone or something");
        productDto.setId(1L);
        productDto.setName("Name");
        productDto.setPrice(new BigDecimal("2.3"));
        when(filterProductUseCase.findById(Mockito.<Long>any())).thenReturn(productDto);
        long productId = 1L;

        GenericResponse<ProductDto> actualFindByIdResult = productService.findById(productId);

        verify(filterProductUseCase).findById(1L);
        ResultResponse result = actualFindByIdResult.getResult();
        List<DetailResponse> details = result.getDetails();
        assertEquals(1, details.size());
        DetailResponse getResult = details.getFirst();
        assertEquals("200 OK", getResult.getDetail());
        assertEquals("200 OK", getResult.getMessage());
        assertEquals("200", getResult.getInternalCode());
        assertEquals("Internal component details", result.getSource());
        BigDecimal expectedCost = new BigDecimal("2.3");
        assertEquals(expectedCost, actualFindByIdResult.getData().getCost());
    }

    @Test
    void testInsert() {
        doNothing().when(insertProductUserCase).insertProduct(Mockito.any());

        ProductDto productDto = new ProductDto();
        productDto.setCategories(new ArrayList<>());
        productDto.setCost(new BigDecimal("2.3"));
        productDto.setCreateDate("2020-03-01");
        productDto.setDescription("The characteristics of someone or something");
        productDto.setId(1L);
        productDto.setName("Name");
        productDto.setPrice(new BigDecimal("2.3"));

        GenericResponse<Void> actualInsertResult = productService.insert(productDto);

        verify(insertProductUserCase).insertProduct(isA(ProductDto.class));
        ResultResponse result = actualInsertResult.getResult();
        List<DetailResponse> details = result.getDetails();
        assertEquals(1, details.size());
        DetailResponse getResult = details.getFirst();
        assertEquals("200 OK", getResult.getDetail());
        assertEquals("200 OK", getResult.getMessage());
        assertEquals("200", getResult.getInternalCode());
        assertEquals("Internal component details", result.getSource());
        assertNull(actualInsertResult.getData());
    }

    @Test
    void testUpdate() {
        doNothing().when(updateProductUserCase).updateProduct(Mockito.any(), Mockito.<Long>any());

        ProductDto productDto = new ProductDto();
        productDto.setCategories(new ArrayList<>());
        productDto.setCost(new BigDecimal("2.3"));
        productDto.setCreateDate("2020-03-01");
        productDto.setDescription("The characteristics of someone or something");
        productDto.setId(1L);
        productDto.setName("Name");
        productDto.setPrice(new BigDecimal("2.3"));
        long productId = 1L;

        GenericResponse<Void> actualUpdateResult = productService.update(productDto, productId);

        verify(updateProductUserCase).updateProduct(isA(ProductDto.class), eq(1L));
        ResultResponse result = actualUpdateResult.getResult();
        List<DetailResponse> details = result.getDetails();
        assertEquals(1, details.size());
        DetailResponse getResult = details.getFirst();
        assertEquals("200 OK", getResult.getDetail());
        assertEquals("200 OK", getResult.getMessage());
        assertEquals("200", getResult.getInternalCode());
        assertEquals("Internal component details", result.getSource());
        assertNull(actualUpdateResult.getData());
    }
}

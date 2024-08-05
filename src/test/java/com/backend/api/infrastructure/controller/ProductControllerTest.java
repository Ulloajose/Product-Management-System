package com.backend.api.infrastructure.controller;

import com.backend.api.application.service.ProductService;
import com.backend.api.application.usercase.FilterProductUserCaseImpl;
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
import com.backend.api.infrastructure.adapter.JpaProductRepositoryAdapter;
import com.backend.api.infrastructure.entity.ProductEntity;
import com.backend.api.infrastructure.repository.CategoryRepository;
import com.backend.api.infrastructure.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {ProductController.class, ProductService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ProductControllerTest {
    @MockBean
    private DeleteProductUserCase deleteProductUserCase;

    @MockBean
    private FilterProductUseCase filterProductUseCase;

    @MockBean
    private InsertProductUserCase insertProductUserCase;

    @Autowired
    private ProductController productController;

    @MockBean
    private UpdateProductUserCase updateProductUserCase;

    @Test
    void testFindAll() {
        DataTablesResponse<ProductDto> dataTablesResponse = new DataTablesResponse<>();
        dataTablesResponse.setCurrentPage(1);
        dataTablesResponse.setData(new ArrayList<>());
        dataTablesResponse.setTotalItems(1L);
        dataTablesResponse.setTotalPages(1);
        JpaProductRepositoryAdapter productRepositoryPort = mock(JpaProductRepositoryAdapter.class);
        when(productRepositoryPort.findAll(anyInt(), anyInt(), Mockito.any(), Mockito.any()))
                .thenReturn(dataTablesResponse);
        ProductController productController = new ProductController(
                new ProductService(new FilterProductUserCaseImpl(productRepositoryPort), mock(DeleteProductUserCase.class),
                        mock(InsertProductUserCase.class), mock(UpdateProductUserCase.class)));
        int pageNumber = 10;
        int pageSize = 3;
        String[] sort = new String[]{"Sort"};
        long id = 1L;
        String product = "Product";
        String category = "Category";
        Month month = Month.JANUARY;

        GenericResponse<DataTablesResponse<ProductDto>> actualFindAllResult = productController.findAll(pageNumber,
                pageSize, sort, id, product, category, month);

        verify(productRepositoryPort).findAll(eq(10), eq(3), isA(String[].class), isA(FilterProductDto.class));
        ResultResponse result = actualFindAllResult.getResult();
        List<DetailResponse> details = result.getDetails();
        assertEquals(1, details.size());
        DetailResponse getResult = details.getFirst();
        assertEquals("200 OK", getResult.getDetail());
        assertEquals("200 OK", getResult.getMessage());
        assertEquals("200", getResult.getInternalCode());
        assertEquals("Internal component details", result.getSource());
        assertEquals(1, actualFindAllResult.getData().getCurrentPage());
    }

    @Test
    void testCreate() throws Exception {
        doNothing().when(insertProductUserCase).insertProduct(Mockito.<ProductDto>any());

        ProductDto productDto = new ProductDto();
        productDto.setCategories(new ArrayList<>());
        productDto.setCost(new BigDecimal("2.3"));
        productDto.setCreateDate("2020-03-01");
        productDto.setDescription("The characteristics of someone or something");
        productDto.setId(1L);
        productDto.setName("Name");
        productDto.setPrice(new BigDecimal("2.3"));
        String content = (new ObjectMapper()).writeValueAsString(productDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(productController).build();

        ResultActions actualPerformResult = buildResult.perform(requestBuilder);

        actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"result\":{\"details\":[{\"internalCode\":\"200\",\"message\":\"200 OK\",\"detail\":\"200 OK\"}],\"source\":\"Internal"
                                        + " component details\"}}"));
    }

    @Test
    void testDelete() {
        doNothing().when(deleteProductUserCase).deleteProduct(Mockito.<Long>any());
        ProductController productController = new ProductController(new ProductService(
                new FilterProductUserCaseImpl(
                        new JpaProductRepositoryAdapter(mock(ProductRepository.class), mock(CategoryRepository.class))),
                deleteProductUserCase, mock(InsertProductUserCase.class), mock(UpdateProductUserCase.class)));
        long productId = 1L;

        GenericResponse<Void> actualDeleteResult = productController.delete(productId);

        verify(deleteProductUserCase).deleteProduct(eq(1L));
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
    void testUpdate() {
        doNothing().when(updateProductUserCase).updateProduct(Mockito.<ProductDto>any(), Mockito.<Long>any());
        ProductController productController = new ProductController(new ProductService(
                new FilterProductUserCaseImpl(
                        new JpaProductRepositoryAdapter(mock(ProductRepository.class), mock(CategoryRepository.class))),
                mock(DeleteProductUserCase.class), mock(InsertProductUserCase.class), updateProductUserCase));

        ProductDto dto = new ProductDto();
        dto.setCategories(new ArrayList<>());
        dto.setCost(new BigDecimal("2.3"));
        dto.setCreateDate("2020-03-01");
        dto.setDescription("The characteristics of someone or something");
        dto.setId(1L);
        dto.setName("Name");
        dto.setPrice(new BigDecimal("2.3"));
        long productId = 1L;

        GenericResponse<Void> actualUpdateResult = productController.update(dto, productId);

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

    @Test
    void testGet() {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setCategories(new HashSet<>());
        productEntity.setCost(new BigDecimal("2.3"));
        productEntity.setCreatedBy(1L);
        productEntity.setCreatedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        productEntity.setDeleted(true);
        productEntity.setDescription("The characteristics of someone or something");
        productEntity.setId(1L);
        productEntity.setLastModifiedBy(1L);
        productEntity.setLastModifiedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        productEntity.setName("Name");
        productEntity.setPrice(new BigDecimal("2.3"));
        Optional<ProductEntity> ofResult = Optional.of(productEntity);
        ProductRepository productRepository = mock(ProductRepository.class);
        when(productRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        ProductController productController = new ProductController(new ProductService(
                new FilterProductUserCaseImpl(
                        new JpaProductRepositoryAdapter(productRepository, mock(CategoryRepository.class))),
                mock(DeleteProductUserCase.class), mock(InsertProductUserCase.class), mock(UpdateProductUserCase.class)));
        long productId = 1L;

        GenericResponse<ProductDto> actualGetResult = productController.get(productId);

        verify(productRepository).findById(eq(1L));
        ProductDto data = actualGetResult.getData();
        assertEquals("1970-01-01T00:00:00Z", data.getCreateDate());
        ResultResponse result = actualGetResult.getResult();
        List<DetailResponse> details = result.getDetails();
        assertEquals(1, details.size());
        DetailResponse getResult = details.getFirst();
        assertEquals("200 OK", getResult.getDetail());
        assertEquals("200 OK", getResult.getMessage());
        assertEquals("200", getResult.getInternalCode());
        assertEquals("Internal component details", result.getSource());
        assertEquals("Name", data.getName());
        assertEquals("The characteristics of someone or something", data.getDescription());
        assertEquals(1L, data.getId().longValue());
        assertTrue(data.getCategories().isEmpty());
        BigDecimal expectedCost = new BigDecimal("2.3");
        assertEquals(expectedCost, data.getCost());
        BigDecimal expectedPrice = new BigDecimal("2.3");
        assertEquals(expectedPrice, data.getPrice());
    }
}

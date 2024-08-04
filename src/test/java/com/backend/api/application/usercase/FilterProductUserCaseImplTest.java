package com.backend.api.application.usercase;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.backend.api.domain.dto.FilterProductDto;
import com.backend.api.domain.dto.ProductDto;
import com.backend.api.domain.model.DataTablesResponse;

import java.time.Month;
import java.util.ArrayList;

import com.backend.api.domain.port.out.ProductRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {FilterProductUserCaseImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class FilterProductUserCaseImplTest {
    @MockBean
    private ProductRepositoryPort productRepositoryPort;

    @Autowired
    private FilterProductUserCaseImpl filterProductUserCase;

    @Test
    void testFindAll() {
        // Arrange
        DataTablesResponse<ProductDto> dataTablesResponse = new DataTablesResponse<>();
        dataTablesResponse.setCurrentPage(1);
        dataTablesResponse.setData(new ArrayList<>());
        dataTablesResponse.setTotalItems(1L);
        dataTablesResponse.setTotalPages(1);
        when(productRepositoryPort.findAll(anyInt(), anyInt(), Mockito.any(), Mockito.any()))
                .thenReturn(dataTablesResponse);
        int pageNumber = 10;
        int pageSize = 3;
        String[] sort = new String[]{"Sort"};
        FilterProductDto filterProductDto = new FilterProductDto(1L, "JaneDoe", "Tag", Month.JANUARY);

        DataTablesResponse<ProductDto> actualFindAllResult = filterProductUserCase.findAll(pageNumber, pageSize, sort,
                filterProductDto);

        verify(productRepositoryPort).findAll(eq(10), eq(3), isA(String[].class), isA(FilterProductDto.class));
        assertSame(dataTablesResponse, actualFindAllResult);
    }
}

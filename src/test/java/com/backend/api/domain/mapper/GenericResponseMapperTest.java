package com.backend.api.domain.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.backend.api.domain.model.DetailResponse;
import com.backend.api.domain.model.GenericResponse;
import com.backend.api.domain.model.ResultResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class GenericResponseMapperTest {

    @Test
    void testMapDetailResponse() {
        HttpStatus status = HttpStatus.NO_CONTENT;
        String message = "Not all who wander are lost";
        String detail = "Detail";

        DetailResponse actualMapDetailResponseResult = GenericResponseMapper.mapDetailResponse(status, message, detail);

        assertEquals("204", actualMapDetailResponseResult.getInternalCode());
        assertEquals("Detail", actualMapDetailResponseResult.getDetail());
        assertEquals("Not all who wander are lost", actualMapDetailResponseResult.getMessage());
    }

    @Test
    void testMapGenericResponse() {
        DetailResponse detailResponse = new DetailResponse("Internal Code", "Not all who wander are lost", "Detail");

        GenericResponse<?> actualMapGenericResponseResult = GenericResponseMapper.mapGenericResponse(detailResponse);

        ResultResponse result = actualMapGenericResponseResult.getResult();
        assertEquals("Internal component details", result.getSource());
        assertNull(actualMapGenericResponseResult.getData());
        assertEquals(1, result.getDetails().size());
    }
}

package com.backend.api.infrastructure.controller;

import com.backend.api.domain.dto.StatisticDto;
import com.backend.api.domain.mapper.GenericResponseMapper;
import com.backend.api.domain.model.GenericResponse;
import com.backend.api.domain.port.in.GetStatisticUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Tag(name = "Statistic", description = "System Statistic management APIs")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class StatisticController {

    private final GetStatisticUseCase getStatisticUseCase;

    @Operation(
            summary = "Get all statistics",
            description = "Get all statistics",
            tags = { "Statistic", "get" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = GenericResponse.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/statistics")
    @ResponseStatus(OK)
    public GenericResponse<List<StatisticDto>> findAll(){
        return GenericResponseMapper.buildGenericResponse(getStatisticUseCase.getStatistic(), HttpStatus.OK, HttpStatus.OK.toString());
    }
}

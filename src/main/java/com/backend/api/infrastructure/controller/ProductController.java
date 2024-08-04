package com.backend.api.infrastructure.controller;

import com.backend.api.application.service.ProductService;
import com.backend.api.domain.dto.FilterProductDto;
import com.backend.api.domain.dto.ProductDto;
import com.backend.api.domain.model.DataTablesResponse;
import com.backend.api.domain.model.GenericResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.Month;

import static org.springframework.http.HttpStatus.OK;

@Tag(name = "Product", description = "Product management APIs")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Operation(
            summary = "Retrieve an product by filters",
            description = "Get a list of Product per filter set. The response is an Product object with name, category and another fields.",
            tags = { "Product", "get" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = GenericResponse.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/products")
    @ResponseStatus(OK)
    public GenericResponse<DataTablesResponse<ProductDto>> findAll(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id,asc") String[] sort,
            @RequestParam(defaultValue = "") Long id,
            @RequestParam(defaultValue = "") String product,
            @RequestParam(defaultValue = "") String category,
            @RequestParam(defaultValue = "") Month month) {

        return productService.findAll(pageNumber, pageSize, sort, FilterProductDto.builder()
                .productName(product)
                .month(month)
                .id(id)
                .category(category)
                .build());
    }

    @Operation(
            summary = "Delete a Product by id",
            description = "Delete a Product by id. This service change Product status to deleted.",
            tags = { "Product", "delete" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = GenericResponse.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @DeleteMapping("/product/{productId}")
    @ResponseStatus(OK)
    public GenericResponse<Void> delete(@PathVariable Long productId){
        return productService.delete(productId);
    }

    @Operation(
            summary = "Update a Product by id",
            description = "Update a Product by id.",
            tags = { "Product", "update" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = GenericResponse.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PutMapping("/product/{productId}")
    @ResponseStatus(OK)
    public GenericResponse<Void> update(@Valid @RequestBody ProductDto dto, @PathVariable Long productId){
        return productService.update(dto, productId);
    }

    @Operation(
            summary = "Get a Product by id",
            description = "Get a Product by id.",
            tags = { "Product", "get" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = GenericResponse.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/product/{productId}")
    @ResponseStatus(OK)
    public GenericResponse<ProductDto> get(@PathVariable Long productId){
        return productService.findById(productId);
    }

    @Operation(
            summary = "Create a Product",
            description = "Create a Product.",
            tags = { "Product", "create" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = GenericResponse.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PostMapping("/product")
    @ResponseStatus(OK)
    public GenericResponse<Void> create(@Valid @RequestBody ProductDto dto){
        return productService.insert(dto);
    }
}

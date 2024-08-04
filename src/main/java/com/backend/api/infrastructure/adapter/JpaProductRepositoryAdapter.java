package com.backend.api.infrastructure.adapter;

import com.backend.api.domain.dto.FilterProductDto;
import com.backend.api.domain.dto.ProductDto;
import com.backend.api.domain.enums.Category;
import com.backend.api.domain.exeption.NotFoundException;
import com.backend.api.domain.mapper.ProductMapper;
import com.backend.api.domain.model.DataTablesResponse;
import com.backend.api.domain.port.out.ProductRepositoryPort;
import com.backend.api.domain.util.PaginationUtil;
import com.backend.api.infrastructure.entity.CategoryEntity;
import com.backend.api.infrastructure.entity.ProductEntity;
import com.backend.api.infrastructure.repository.CategoryRepository;
import com.backend.api.infrastructure.repository.ProductRepository;
import com.backend.api.infrastructure.repository.specification.ProductSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static com.backend.api.domain.util.Constant.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class JpaProductRepositoryAdapter implements ProductRepositoryPort {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public void deleteProduct(Long productId, Long userId) {
        ProductEntity productEntity = getProductEntity(productId);
        productEntity.setDeleted(true);
        productEntity.setCreatedBy(userId);
        productEntity.setLastModifiedBy(userId);
        productEntity.setLastModifiedDate(LocalDateTime.now());
        productRepository.save(productEntity);
    }

    @Override
    public DataTablesResponse<ProductDto> findAll(int pageNumber, int pageSize, String[] sort, FilterProductDto filter) {
        Pageable pageable = getPageable(pageNumber, pageSize, sort);

        Specification<ProductEntity> specNoDeleted = ProductSpecification.notDeleted();
        Specification<ProductEntity> specId = ProductSpecification.productId(filter.getId());
        Specification<ProductEntity> specProductName = ProductSpecification.productNameLike(filter.getProductName());
        Specification<ProductEntity> specCategoryName = ProductSpecification.categoryNameLike(filter.getCategory());
        Specification<ProductEntity> specMonth = ProductSpecification.byMonth(filter.getMonth());

        Specification<ProductEntity> combinedSpec = Specification.where(specProductName)
                .and(specId).and(specCategoryName).and(specMonth).and(specNoDeleted);

        Page<ProductEntity> entityPage = productRepository.findAll(combinedSpec, pageable);
        List<ProductDto> bList = entityPage.stream()
                .map(ProductMapper::map)
                .toList();

        DataTablesResponse<ProductDto> tablesResponse = new DataTablesResponse<>();
        tablesResponse.setData(bList);
        tablesResponse.setCurrentPage(entityPage.getNumber());
        tablesResponse.setTotalItems(entityPage.getTotalElements());
        tablesResponse.setTotalPages(entityPage.getTotalPages());

        return tablesResponse;
    }

    @Override
    public void insertProduct(ProductDto productDto, Long userId) {
        ProductEntity productEntity = ProductMapper.map(productDto, getCategoryEntities(productDto.getCategories()));
        productEntity.setCreatedBy(userId);
        productEntity.setCreatedDate(LocalDateTime.now());
        productRepository.save(productEntity);
    }

    @Override
    public void updateProduct(ProductDto productDto, Long userId, Long productId) {
        ProductEntity productEntity = getProductEntity(productId);
        productEntity.setLastModifiedBy(userId);
        productEntity.setLastModifiedDate(LocalDateTime.now());
        productEntity.setName(productDto.getName());
        productEntity.setCost(productDto.getCost());
        productEntity.setPrice(productDto.getPrice());
        productEntity.setDescription(productDto.getDescription());
        productEntity.setCategories(getCategoryEntities(productDto.getCategories()));
        productRepository.save(productEntity);
    }

    @Override
    public ProductDto findProductById(Long productId) {
        return ProductMapper.map(getProductEntity(productId));
    }

    private Pageable getPageable(int pageNumber, int pageSize, String[] sort){
        List<Sort.Order> orders = PaginationUtil.setSort(sort);
        return PageRequest.of(pageNumber, pageSize, Sort.by(orders));
    }

    private ProductEntity getProductEntity(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(PRODUCT_NOT_FOUND));
    }

    private Set<CategoryEntity> getCategoryEntities(List<Category> categories) {
        List<String> strings = categories.stream().map(Category::name).toList();
        return categoryRepository.findByNameIn(strings);
    }
}

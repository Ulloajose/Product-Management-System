package com.backend.api.application.listener;

import com.backend.api.application.config.security.IAuthenticationFacade;
import com.backend.api.application.event.OnCreateProductEvent;
import com.backend.api.domain.dto.ProductDto;
import com.backend.api.domain.dto.StatisticDto;
import com.backend.api.domain.enums.Category;
import com.backend.api.domain.enums.StatisticType;
import com.backend.api.domain.model.User;
import com.backend.api.domain.port.out.StatisticRepositoryPort;
import com.backend.api.domain.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class CreateProductListener {

    private final StatisticRepositoryPort statisticRepositoryPort;
    private final IAuthenticationFacade authenticationFacade;
    private final UserRepositoryPort userRepositoryPort;

    @EventListener
    @Async
    public void handle(OnCreateProductEvent event) {
        ProductDto productDto = event.getProduct();
        for (Category category : productDto.getCategories()) {
            updateTotalProducts(category);
            updateMostExpensiveProduct(category, productDto.getPrice());
            updateCheapestProduct(category, productDto.getPrice());
        }
    }

    private void updateTotalProducts(Category category) {
        StatisticDto statisticDto = getOrCreateStatistic(StatisticType.TOTAL_PRODUCTS, category);
        statisticDto.setValue(statisticDto.getValue().add(BigDecimal.ONE));
        statisticRepositoryPort.save(statisticDto, getUserId());
    }

    private void updateMostExpensiveProduct(Category category, BigDecimal price) {
        StatisticDto statisticDto = getOrCreateStatistic(StatisticType.MOST_EXPENSIVE, category);
        if (statisticDto.getValue().compareTo(price) < 0) {
            statisticDto.setValue(price);
            statisticRepositoryPort.save(statisticDto, getUserId());
        }
    }

    private void updateCheapestProduct(Category category, BigDecimal price) {
        StatisticDto statisticDto = getOrCreateStatistic(StatisticType.CHEAPEST_PRODUCT, category);
        if (statisticDto.getValue().compareTo(price) > 0 || statisticDto.getValue().compareTo(BigDecimal.ZERO) == 0) {
            statisticDto.setValue(price);
            statisticRepositoryPort.save(statisticDto, getUserId());
        }
    }

    private StatisticDto getOrCreateStatistic(StatisticType statisticType, Category category) {
        StatisticDto statisticDto = statisticRepositoryPort.find(statisticType, category);

        if (statisticDto == null) {
            statisticDto = new StatisticDto();
            statisticDto.setType(statisticType);
            statisticDto.setCategory(category);
            statisticDto.setValue(BigDecimal.ZERO);
        }
        return statisticDto;
    }

    private Long getUserId(){
        String username = authenticationFacade.getAuthentication().getName();
        User user = userRepositoryPort.findByUsername(username);
        return user.getId();
    }
}

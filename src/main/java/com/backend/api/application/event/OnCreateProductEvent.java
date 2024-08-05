package com.backend.api.application.event;

import com.backend.api.domain.dto.ProductDto;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class OnCreateProductEvent extends ApplicationEvent {
    private final ProductDto product;
    private final Long userId;

    public OnCreateProductEvent(Object source, ProductDto product, Long userId) {
        super(source);
        this.product = product;
        this.userId = userId;
    }
}

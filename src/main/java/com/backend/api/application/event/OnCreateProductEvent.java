package com.backend.api.application.event;

import com.backend.api.domain.dto.ProductDto;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class OnCreateProductEvent extends ApplicationEvent {
    private final ProductDto product;

    public OnCreateProductEvent(Object source, ProductDto product) {
        super(source);
        this.product = product;
    }
}

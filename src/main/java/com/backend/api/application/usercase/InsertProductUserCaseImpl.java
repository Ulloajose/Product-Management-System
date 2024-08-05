package com.backend.api.application.usercase;

import com.backend.api.application.config.security.IAuthenticationFacade;
import com.backend.api.application.event.OnCreateProductEvent;
import com.backend.api.domain.dto.ProductDto;
import com.backend.api.domain.model.User;
import com.backend.api.domain.port.in.InsertProductUserCase;
import com.backend.api.domain.port.out.ProductRepositoryPort;
import com.backend.api.domain.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InsertProductUserCaseImpl implements InsertProductUserCase {

    private final ProductRepositoryPort productRepositoryPort;
    private final IAuthenticationFacade authenticationFacade;
    private final UserRepositoryPort userRepositoryPort;
    private final ApplicationEventPublisher publisher;

    @Override
    public void insertProduct(ProductDto productDto) {
        String username = authenticationFacade.getAuthentication().getName();
        User user = userRepositoryPort.findByUsername(username);
        productDto = productRepositoryPort.insertProduct(productDto, user.getId());
        publisher.publishEvent(new OnCreateProductEvent(this, productDto, user.getId()));
    }
}

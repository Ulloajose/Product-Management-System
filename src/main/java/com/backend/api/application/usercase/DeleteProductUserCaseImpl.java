package com.backend.api.application.usercase;

import com.backend.api.application.config.security.IAuthenticationFacade;
import com.backend.api.domain.model.User;
import com.backend.api.domain.port.in.DeleteProductUserCase;
import com.backend.api.domain.port.out.ProductRepositoryPort;
import com.backend.api.domain.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteProductUserCaseImpl implements DeleteProductUserCase {

    private final ProductRepositoryPort productRepositoryPort;
    private final IAuthenticationFacade authenticationFacade;
    private final UserRepositoryPort userRepositoryPort;

    @Override
    public void deleteProduct(Long productId) {

        String username = authenticationFacade.getAuthentication().getName();
        User user = userRepositoryPort.findByUsername(username);
        productRepositoryPort.deleteProduct(productId, user.getId());
    }
}

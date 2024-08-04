package com.backend.api.application.usercase;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.backend.api.application.config.security.IAuthenticationFacade;
import com.backend.api.domain.model.User;
import com.backend.api.domain.port.out.ProductRepositoryPort;
import com.backend.api.domain.port.out.UserRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {DeleteProductUserCaseImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class DeleteProductUserCaseImplTest {
    @MockBean
    private ProductRepositoryPort productRepositoryPort;

    @Autowired
    private DeleteProductUserCaseImpl deleteProductUserCase;

    @MockBean
    private IAuthenticationFacade iAuthenticationFacade;

    @MockBean
    private UserRepositoryPort userRepositoryPort;


    @Test
    void testDeleteArticle() {
        doNothing().when(productRepositoryPort).deleteProduct(Mockito.any(), Mockito.<Long>any());
        when(iAuthenticationFacade.getAuthentication())
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        when(userRepositoryPort.findByUsername(Mockito.any())).thenReturn(new User());
        Long productId = 42L;

        deleteProductUserCase.deleteProduct(productId);

        verify(iAuthenticationFacade).getAuthentication();
        verify(productRepositoryPort).deleteProduct(eq(productId), isNull());
        verify(userRepositoryPort).findByUsername("Principal");
    }
}

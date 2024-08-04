package com.backend.api.application.config.security.jwt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {JwtAccessDeniedHandler.class})
@ExtendWith(SpringExtension.class)
class JwtAccessDeniedHandlerTest {
    @Autowired
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Test
    void testHandle() throws IOException {
        // Arrange
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        AccessDeniedException accessDeniedException = new AccessDeniedException("Msg");

        jwtAccessDeniedHandler.handle(request, response, accessDeniedException);

        assertEquals("Access denied", response.getErrorMessage());
        assertEquals(403, response.getStatus());
        assertTrue(response.isCommitted());
    }
}

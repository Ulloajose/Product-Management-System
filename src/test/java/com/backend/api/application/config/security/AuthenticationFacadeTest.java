package com.backend.api.application.config.security;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AuthenticationFacade.class})
@ExtendWith(SpringExtension.class)
class AuthenticationFacadeTest {
    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Test
    void testGetAuthentication() {
        assertNull(authenticationFacade.getAuthentication());
    }
}

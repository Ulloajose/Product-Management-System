package com.backend.api.domain.exeption;

import java.io.Serial;

public class AuthenticationJWTException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public AuthenticationJWTException(String message) {
        super(message);
    }
}

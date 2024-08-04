package com.backend.api.domain.port.out;

import com.backend.api.domain.model.User;

public interface UserRepositoryPort {
    User findByUsername(String username);
}

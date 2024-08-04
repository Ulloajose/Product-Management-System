package com.backend.api.infrastructure.adapter;

import com.backend.api.domain.mapper.UserMapper;
import com.backend.api.domain.model.User;
import com.backend.api.domain.port.out.UserRepositoryPort;
import com.backend.api.domain.util.Constant;
import com.backend.api.infrastructure.entity.UserEntity;
import com.backend.api.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JpaUserRepositoryAdapter implements UserRepositoryPort {

    private final UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(Constant.USER_NOT_FOUND));

        return UserMapper.map(userEntity);
    }
}

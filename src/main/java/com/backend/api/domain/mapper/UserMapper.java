package com.backend.api.domain.mapper;

import com.backend.api.domain.model.User;
import com.backend.api.infrastructure.entity.UserEntity;
import lombok.experimental.UtilityClass;

import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class UserMapper {

    public User map(UserEntity user){

        Set<String> roles = user.getRoles().stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toSet());

        return User.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(roles)
                .build();
    }
}

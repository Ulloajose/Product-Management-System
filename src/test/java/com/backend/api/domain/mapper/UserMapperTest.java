package com.backend.api.domain.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.backend.api.domain.model.User;
import com.backend.api.infrastructure.entity.UserEntity;

import java.time.LocalDate;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

class UserMapperTest {

    @Test
    void testMap() {
        UserEntity user = new UserEntity();
        user.setCreatedBy(1L);
        user.setCreatedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        user.setDeleted(true);
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setLastModifiedBy(1L);
        user.setLastModifiedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        user.setPassword("iloveyou");
        user.setRoles(new HashSet<>());
        user.setUsername("janedoe");

        User actualMapResult = UserMapper.map(user);

        assertEquals("iloveyou", actualMapResult.getPassword());
        assertEquals("jane.doe@example.org", actualMapResult.getEmail());
        assertEquals("janedoe", actualMapResult.getUsername());
        assertEquals(1L, actualMapResult.getId().longValue());
        assertTrue(actualMapResult.getRoles().isEmpty());
    }
}

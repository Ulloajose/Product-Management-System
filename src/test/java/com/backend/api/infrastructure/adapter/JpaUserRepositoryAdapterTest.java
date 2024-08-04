package com.backend.api.infrastructure.adapter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.backend.api.domain.model.User;
import com.backend.api.infrastructure.entity.UserEntity;
import com.backend.api.infrastructure.repository.UserRepository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {JpaUserRepositoryAdapter.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class JpaUserRepositoryAdapterTest {
    @Autowired
    private JpaUserRepositoryAdapter jpaUserRepositoryAdapter;

    @MockBean
    private UserRepository userRepository;

    @Test
    void testFindByUsername() {
        UserEntity userEntity = getUserEntity();
        Optional<UserEntity> ofResult = Optional.of(userEntity);
        when(userRepository.findByUsername(Mockito.any())).thenReturn(ofResult);
        String username = "janedoe";

        User actualFindByUsernameResult = jpaUserRepositoryAdapter.findByUsername(username);

        verify(userRepository).findByUsername(username);
        assertEquals("iloveyou", actualFindByUsernameResult.getPassword());
        assertEquals("jane.doe@example.org", actualFindByUsernameResult.getEmail());
        assertEquals("janedoe", actualFindByUsernameResult.getUsername());
        assertEquals(1L, actualFindByUsernameResult.getId().longValue());
        assertTrue(actualFindByUsernameResult.getRoles().isEmpty());
    }

    private static UserEntity getUserEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setCreatedBy(1L);
        userEntity.setCreatedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        userEntity.setDeleted(true);
        userEntity.setEmail("jane.doe@example.org");
        userEntity.setId(1L);
        userEntity.setLastModifiedBy(1L);
        userEntity.setLastModifiedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        userEntity.setPassword("iloveyou");
        userEntity.setRoles(new HashSet<>());
        userEntity.setUsername("janedoe");
        return userEntity;
    }

    @Test
    void testFindByUsernameNotFound() {
        when(userRepository.findByUsername(Mockito.any()))
                .thenThrow(new UsernameNotFoundException("User does not exist."));
        String username = "janedoe";

        assertThrows(UsernameNotFoundException.class, () -> jpaUserRepositoryAdapter.findByUsername(username));
        verify(userRepository).findByUsername(username);
    }
}

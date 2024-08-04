package com.backend.api.application.usercase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.backend.api.domain.model.User;
import com.backend.api.domain.port.out.UserRepositoryPort;

import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AuthenticateUserCaseImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class AuthenticateUserCaseImplTest {
    @Autowired
    private AuthenticateUserCaseImpl authenticateUserCaseImpl;

    @MockBean
    private UserRepositoryPort userRepositoryPort;

    @Test
    void testLoadUserByUsername() throws UsernameNotFoundException {
        HashSet<String> roles = new HashSet<>();
        when(userRepositoryPort.findByUsername(Mockito.any()))
                .thenReturn(new User(1L, "jane.doe@example.org", "janedoe", "iloveyou", roles));
        String username = "janedoe";

        UserDetails actualLoadUserByUsernameResult = authenticateUserCaseImpl.loadUserByUsername(username);

        verify(userRepositoryPort).findByUsername(username);
        assertEquals("iloveyou", actualLoadUserByUsernameResult.getPassword());
        assertEquals("janedoe", actualLoadUserByUsernameResult.getUsername());
        assertTrue(actualLoadUserByUsernameResult.isAccountNonExpired());
        assertTrue(actualLoadUserByUsernameResult.isAccountNonLocked());
        assertTrue(actualLoadUserByUsernameResult.isCredentialsNonExpired());
        assertTrue(actualLoadUserByUsernameResult.isEnabled());
    }


    @Test
    void testLoadUserByUsernameNotFound() throws UsernameNotFoundException {
        when(userRepositoryPort.findByUsername(Mockito.any())).thenThrow(new UsernameNotFoundException("Msg"));
        String username = "janedoe";

        assertThrows(UsernameNotFoundException.class, () -> authenticateUserCaseImpl.loadUserByUsername(username));
        verify(userRepositoryPort).findByUsername(username);
    }
}

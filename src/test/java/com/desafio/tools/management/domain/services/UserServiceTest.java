package com.desafio.tools.management.domain.services;

import com.desafio.tools.management.application.web.entities.requests.UserRequest;
import com.desafio.tools.management.domain.exceptions.AlreadyExistsException;
import com.desafio.tools.management.domain.services.impl.UserServiceImpl;
import com.desafio.tools.management.resources.entities.UserEntity;
import com.desafio.tools.management.resources.repositories.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserServiceImpl service;

    @Mock
    private UsersRepository usersRepository;

    private UserEntity userEntityWithId;
    private UserRequest userRequest;

    @BeforeEach
    void init() {
        userEntityWithId = new UserEntity(
                1L,
                "username",
                "pass"
        );

        userRequest = new UserRequest(
                "username",
                "pass"
        );
    }

    @Test
    void should_create_tool() throws AlreadyExistsException {
        when(usersRepository.existsByUsername(any(String.class))).thenReturn(false);

        when(usersRepository.save(any(UserEntity.class))).thenReturn(userEntityWithId);

        final var actualUser = service.createUser(userRequest);

        assertThat(actualUser.getUserId()).isEqualTo(userEntityWithId.getUserId());
        verify(usersRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void should_not_create_tool_when_already_exists() {
        when(usersRepository.existsByUsername(any(String.class))).thenReturn(true);

        String expectedMessage = "User already exists";

        Exception ex = assertThrows(AlreadyExistsException.class, () -> {
            service.createUser(userRequest);
        });

        assertTrue(ex.getMessage().contains(expectedMessage));
        verify(usersRepository, times(1)).existsByUsername(any(String.class));
    }

}
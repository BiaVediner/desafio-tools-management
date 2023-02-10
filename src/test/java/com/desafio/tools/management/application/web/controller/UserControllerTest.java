package com.desafio.tools.management.application.web.controller;

import com.desafio.tools.management.application.web.entities.requests.UserRequest;
import com.desafio.tools.management.domain.exceptions.AlreadyExistsException;
import com.desafio.tools.management.domain.exceptions.TagsLimitExceeded;
import com.desafio.tools.management.domain.services.impl.UserServiceImpl;
import com.desafio.tools.management.resources.entities.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController controller;

    @Mock
    private UserServiceImpl userService;

    private UserEntity userWithId;
    private UserRequest userRequest;

    @BeforeEach
    void init() {
        userWithId = new UserEntity(
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
    void should_create_tool_return_201() throws AlreadyExistsException, TagsLimitExceeded {
        when(userService.createUser(any(UserRequest.class))).thenReturn(userWithId);

        final var response = controller.createUser(userRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getMessage()).isEqualTo("Success");
    }

    @Test
    void should_not_create_when_already_exists_return_400() throws AlreadyExistsException, TagsLimitExceeded {
        AlreadyExistsException ex = new AlreadyExistsException("User already exists");

        when(userService.createUser(any(UserRequest.class))).thenThrow(ex);

        final var response = controller.createUser(userRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().getMessage()).isEqualTo("User already exists");
    }
}
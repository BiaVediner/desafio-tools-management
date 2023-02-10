package com.desafio.tools.management.application.web.controller;

import com.desafio.tools.management.application.web.entities.requests.UserRequest;
import com.desafio.tools.management.application.web.entities.responses.CreateUserResponse;
import com.desafio.tools.management.domain.exceptions.AlreadyExistsException;
import com.desafio.tools.management.domain.services.impl.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@Api(value="UserController", description="Operations for user")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @PostMapping
    @ApiOperation(value = "Create User", response = CreateUserResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful creation"),
            @ApiResponse(code = 400, message = "User already exists"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody @Valid UserRequest createUserRequest) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new CreateUserResponse(userService.createUser(createUserRequest))
            );
        } catch (AlreadyExistsException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new CreateUserResponse(ex)
            );
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new CreateUserResponse(ex)
            );
        }
    }
}

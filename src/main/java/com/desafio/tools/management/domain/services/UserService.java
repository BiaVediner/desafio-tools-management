package com.desafio.tools.management.domain.services;

import com.desafio.tools.management.application.web.entities.requests.UserRequest;
import com.desafio.tools.management.domain.exceptions.AlreadyExistsException;
import com.desafio.tools.management.resources.entities.UserEntity;

public interface UserService {
    UserEntity createUser(UserRequest user) throws AlreadyExistsException;
}

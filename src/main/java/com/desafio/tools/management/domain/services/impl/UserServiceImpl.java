package com.desafio.tools.management.domain.services.impl;

import com.desafio.tools.management.application.web.entities.requests.UserRequest;
import com.desafio.tools.management.domain.exceptions.AlreadyExistsException;
import com.desafio.tools.management.domain.services.UserService;
import com.desafio.tools.management.resources.entities.UserEntity;
import com.desafio.tools.management.resources.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserEntity createUser(UserRequest user) throws AlreadyExistsException {
        validateUsername(user.getUsername());

        return usersRepository.save(
            new UserEntity(user)
        );
    }

    private void validateUsername(String username) throws AlreadyExistsException {
        if(usersRepository.existsByUsername(username)) {
            throw new AlreadyExistsException("User already exists");
        }
    }
}

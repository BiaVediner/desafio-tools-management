package com.desafio.tools.management.application.web.entities.responses;

import com.desafio.tools.management.resources.entities.UserEntity;

public class CreateUserResponse extends Response {
    private UserEntity user;

    public CreateUserResponse(UserEntity user) {
        super("Success");
        this.user = user;
    }

    public CreateUserResponse(Exception ex) {
        super(ex.getMessage());
    }

    public UserEntity getUser() {
        return user;
    }
}
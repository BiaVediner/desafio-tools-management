package com.desafio.tools.management.application.web.entities.responses;

public abstract class Response {
    private final String message;

    public Response(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
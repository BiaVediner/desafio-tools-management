package com.desafio.tools.management.application.web.entities.responses;

public class AuthResponse extends Response {
    private String token;

    public AuthResponse(String token) {
        super("Success");
        this.token = token;
    }

    public AuthResponse(Exception ex) {
        super(ex.getMessage());
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

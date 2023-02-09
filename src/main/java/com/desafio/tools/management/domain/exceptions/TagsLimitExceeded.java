package com.desafio.tools.management.domain.exceptions;

public class TagsLimitExceeded extends Exception{
    public TagsLimitExceeded(String message) {
        super(message);
    }
}
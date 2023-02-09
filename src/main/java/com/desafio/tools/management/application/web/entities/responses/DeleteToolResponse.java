package com.desafio.tools.management.application.web.entities.responses;

import com.desafio.tools.management.domain.entities.Tools;

public class DeleteToolResponse extends Response {
    public DeleteToolResponse(Exception ex) {
        super(ex.getMessage());
    }
}

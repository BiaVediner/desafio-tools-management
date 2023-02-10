package com.desafio.tools.management.application.web.entities.responses;

import com.desafio.tools.management.domain.entities.Tools;

public class CreateToolResponse extends Response {
    private Tools tool;

    public CreateToolResponse(Tools tool) {
        super("Success");
        this.tool = tool;
    }

    public CreateToolResponse(Exception ex) {
        super(ex.getMessage());
    }

    public Tools getTool() {
        return tool;
    }
}

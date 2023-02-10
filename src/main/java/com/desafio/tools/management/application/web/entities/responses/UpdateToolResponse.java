package com.desafio.tools.management.application.web.entities.responses;

import com.desafio.tools.management.domain.entities.Tools;

public class UpdateToolResponse extends Response {
    private Tools tool;

    public UpdateToolResponse(Tools tool) {
        super("Success");
        this.tool = tool;
    }

    public UpdateToolResponse(Exception ex) {
        super(ex.getMessage());
    }

    public Tools getTool() {
        return tool;
    }
}
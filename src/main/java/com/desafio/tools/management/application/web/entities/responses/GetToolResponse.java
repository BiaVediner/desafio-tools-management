package com.desafio.tools.management.application.web.entities.responses;

import com.desafio.tools.management.domain.entities.Tools;

public class GetToolResponse extends Response {
    private Tools tool;

    public GetToolResponse(Tools tool) {
        super("Success");
        this.tool = tool;
    }

    public GetToolResponse(Exception ex) {
        super(ex.getMessage());
    }

    public Tools getTool() {
        return tool;
    }
}

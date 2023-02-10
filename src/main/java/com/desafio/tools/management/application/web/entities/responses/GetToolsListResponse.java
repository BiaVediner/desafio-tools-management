package com.desafio.tools.management.application.web.entities.responses;

import com.desafio.tools.management.domain.entities.Tools;

import java.util.List;

public class GetToolsListResponse extends Response {
    private List<Tools> tools;

    public GetToolsListResponse(List<Tools> tool) {
        super("Success");
        this.tools = tool;
    }

    public GetToolsListResponse(Exception ex) {
        super(ex.getMessage());
    }

    public List<Tools> getTools() {
        return tools;
    }
}

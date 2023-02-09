package com.desafio.tools.management.domain.services;

import com.desafio.tools.management.application.web.entities.requests.ToolRequest;
import com.desafio.tools.management.domain.entities.Tools;
import com.desafio.tools.management.domain.exceptions.AlreadyExistsException;
import com.desafio.tools.management.domain.exceptions.NotFoundException;
import com.desafio.tools.management.domain.exceptions.TagsLimitExceeded;

import java.util.List;

public interface ToolsService {
    Tools createTool(ToolRequest createToolRequest) throws AlreadyExistsException, TagsLimitExceeded;
    Tools getToolById(Long id) throws NotFoundException;
    List<Tools> getTools();
    Tools updateTool(Long id, ToolRequest updateTool) throws NotFoundException, TagsLimitExceeded;
    void deleteTool(Long id) throws NotFoundException;
}

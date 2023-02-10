package com.desafio.tools.management.application.web.controller;

import com.desafio.tools.management.application.web.entities.requests.ToolRequest;
import com.desafio.tools.management.application.web.entities.responses.*;
import com.desafio.tools.management.domain.exceptions.AlreadyExistsException;
import com.desafio.tools.management.domain.exceptions.NotFoundException;
import com.desafio.tools.management.domain.exceptions.TagsLimitExceeded;
import com.desafio.tools.management.domain.services.impl.ToolsServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/tools")
@Api(value="ToolsController", description="Operations for tools")
public class ToolsController {
    @Autowired
    private ToolsServiceImpl toolsService;

    @PostMapping
    @ApiOperation(value = "Create tools", response = CreateToolResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful creation"),
            @ApiResponse(code = 400, message = "Tags amount is higher than 8 or Tool already exists"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<CreateToolResponse> createTool(@RequestBody @Valid ToolRequest createToolRequest) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new CreateToolResponse(toolsService.createTool(createToolRequest))
            );
        } catch (AlreadyExistsException | TagsLimitExceeded ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new CreateToolResponse(ex)
            );
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new CreateToolResponse(ex)
            );
        }
    }

    @GetMapping
    @ApiOperation(value = "List all tools created", response = GetToolsListResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")
    })
    public ResponseEntity<GetToolsListResponse> getTools() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new GetToolsListResponse(toolsService.getTools())
            );
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new GetToolsListResponse(ex)
            );
        }
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get specific tool by id", response = GetToolResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Tool not found"),
            @ApiResponse(code = 403, message = "Forbidden")
    })
    public ResponseEntity<GetToolResponse> getTool(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new GetToolResponse(toolsService.getToolById(id))
            );
        } catch (NotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new GetToolResponse(ex)
            );
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new GetToolResponse(ex)
            );
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete specific tool by id", response = DeleteToolResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Tool not found"),
            @ApiResponse(code = 403, message = "Forbidden")
    })
    public ResponseEntity<DeleteToolResponse> deleteTool(@PathVariable Long id) {
        try {
            toolsService.deleteTool(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (NotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new DeleteToolResponse(ex)
            );
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new DeleteToolResponse(ex)
            );
        }
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update specific tool by id", response = UpdateToolResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation"),
            @ApiResponse(code = 400, message = "Tags amount is higher than 8"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Tool not found"),
            @ApiResponse(code = 403, message = "Forbidden")
    })
    public ResponseEntity<UpdateToolResponse> updateTool(@PathVariable Long id, @RequestBody @Valid ToolRequest toolRequest) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new UpdateToolResponse(toolsService.updateTool(id, toolRequest))
            );
        } catch (NotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new UpdateToolResponse(ex)
            );
        } catch (TagsLimitExceeded ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new UpdateToolResponse(ex)
            );
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new UpdateToolResponse(ex)
            );
        }
    }
}

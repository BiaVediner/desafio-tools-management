package com.desafio.tools.management.application.web.controller;

import com.desafio.tools.management.application.web.entities.requests.ToolRequest;
import com.desafio.tools.management.application.web.entities.responses.*;
import com.desafio.tools.management.domain.exceptions.AlreadyExistsException;
import com.desafio.tools.management.domain.exceptions.NotFoundException;
import com.desafio.tools.management.domain.exceptions.TagsLimitExceeded;
import com.desafio.tools.management.domain.services.impl.ToolsServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tools")
public class ToolsController {
    @Autowired
    private ToolsServiceImpl toolsService;

    @PostMapping
    @Operation(summary = "Create tools")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful creation"),
            @ApiResponse(responseCode = "400", description = "Tags amount is higher than 8 or Tool already exists"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
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
    @Operation(summary = "List all tools created")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
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
    @Operation(summary = "Get specific tool by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Tool not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
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
    @Operation(summary = "Delete specific tool by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Tool not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
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
    @Operation(summary = "Update specific tool by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Tool not found"),
            @ApiResponse(responseCode = "400", description = "Tags amount is higher than 8"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
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

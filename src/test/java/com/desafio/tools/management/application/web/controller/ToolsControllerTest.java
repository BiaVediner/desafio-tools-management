package com.desafio.tools.management.application.web.controller;

import com.desafio.tools.management.application.web.entities.requests.ToolRequest;
import com.desafio.tools.management.domain.entities.Tools;
import com.desafio.tools.management.domain.entities.enums.ToolsStatus;
import com.desafio.tools.management.domain.exceptions.AlreadyExistsException;
import com.desafio.tools.management.domain.exceptions.NotFoundException;
import com.desafio.tools.management.domain.exceptions.TagsLimitExceeded;
import com.desafio.tools.management.domain.services.impl.ToolsServiceImpl;
import com.desafio.tools.management.resources.entities.ToolsEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ToolsControllerTest {

    @InjectMocks
    private ToolsController controller;

    @Mock
    private ToolsServiceImpl toolsService;

    private Tools toolsWithId;
    private ToolRequest toolRequest;
    private ToolRequest invalidToolRequest;

    @BeforeEach
    void init() {
        toolsWithId = new Tools(
                new ToolsEntity(
                1L,
                "tool_request",
                "www.tool_request",
                "tool_request",
                ToolsStatus.CREATED,
                LocalDateTime.now()
            )
        );

        toolRequest = new ToolRequest(
                "tool_request",
                "www.tool_request",
                "tool_request",
                List.of("tag1", "tag2")
        );

        invalidToolRequest = new ToolRequest(
                "tool_request",
                "www.tool_request",
                "tool_request",
                List.of("tag1", "tag2", "tag3", "tag4", "tag5", "tag6", "tag7", "tag8", "tag9")
        );
    }

    @Test
    void should_create_tool_return_201() throws AlreadyExistsException, TagsLimitExceeded {
        when(toolsService.createTool(any(ToolRequest.class))).thenReturn(toolsWithId);

        final var response = controller.createTool(toolRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getMessage()).isEqualTo("Success");
    }

    @Test
    void should_not_create_when_already_exists_return_400() throws AlreadyExistsException, TagsLimitExceeded {
        AlreadyExistsException ex = new AlreadyExistsException("Tool already exists");

        when(toolsService.createTool(any(ToolRequest.class))).thenThrow(ex);

        final var response = controller.createTool(toolRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().getMessage()).isEqualTo("Tool already exists");
    }

    @Test
    void should_not_create_when_tags_amount_invalid_return_400() throws AlreadyExistsException, TagsLimitExceeded {
        TagsLimitExceeded ex = new TagsLimitExceeded("Amount of tags is higher than 8");

        when(toolsService.createTool(any(ToolRequest.class))).thenThrow(ex);

        final var response = controller.createTool(invalidToolRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().getMessage()).isEqualTo("Amount of tags is higher than 8");
    }

    @Test
    void should_get_tool_by_id_return_200() throws NotFoundException {
        when(toolsService.getToolById(ArgumentMatchers.eq(1L))).thenReturn(toolsWithId);

        final var response = controller.getTool(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getMessage()).isEqualTo("Success");
    }

    @Test
    void should_not_get_tool_by_id_when_not_found_return_404() throws NotFoundException {
        NotFoundException ex = new NotFoundException("tool does not exist");

        when(toolsService.getToolById(ArgumentMatchers.eq(1L))).thenThrow(ex);

        final var response = controller.getTool(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody().getMessage()).isEqualTo("tool does not exist");
    }

    @Test
    void should_list_tools_return_200() {
        when(toolsService.getTools()).thenReturn(List.of(toolsWithId));

        final var response = controller.getTools();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getMessage()).isEqualTo("Success");
    }

    @Test
    void should_update_tools_return_200() throws TagsLimitExceeded, NotFoundException {
        when(toolsService.updateTool(ArgumentMatchers.eq(1L), any(ToolRequest.class))).thenReturn(toolsWithId);

        final var response = controller.updateTool(1L, toolRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getMessage()).isEqualTo("Success");
    }

    @Test
    void should_not_update_tools_when_not_found_return_404() throws NotFoundException, TagsLimitExceeded {
        NotFoundException ex = new NotFoundException("tool does not exist");

        when(toolsService.updateTool(ArgumentMatchers.eq(1L), any(ToolRequest.class))).thenThrow(ex);

        final var response = controller.updateTool(1L, toolRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody().getMessage()).isEqualTo("tool does not exist");
    }

    @Test
    void should_not_update_tools_when_tags_amount_invalid_return_400() throws NotFoundException, TagsLimitExceeded {
        TagsLimitExceeded ex = new TagsLimitExceeded("Amount of tags is higher than 8");

        when(toolsService.updateTool(ArgumentMatchers.eq(1L), any(ToolRequest.class))).thenThrow(ex);

        final var response = controller.updateTool(1L, invalidToolRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().getMessage()).isEqualTo("Amount of tags is higher than 8");
    }

    @Test
    void should_not_delete_tools_when_not_found_return_404() throws NotFoundException {
        NotFoundException ex = new NotFoundException("tool does not exist");

        doThrow(ex).when(toolsService).deleteTool(1L);

        final var response = controller.deleteTool(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody().getMessage()).isEqualTo("tool does not exist");
    }

    @Test
    void should_delete_tools_return_200() {
        final var response = controller.deleteTool(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
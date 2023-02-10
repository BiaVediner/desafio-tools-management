package com.desafio.tools.management.domain.services;

import com.desafio.tools.management.application.web.entities.requests.ToolRequest;
import com.desafio.tools.management.domain.entities.Tools;
import com.desafio.tools.management.domain.entities.enums.ToolsStatus;
import com.desafio.tools.management.domain.exceptions.AlreadyExistsException;
import com.desafio.tools.management.domain.exceptions.NotFoundException;
import com.desafio.tools.management.domain.exceptions.TagsLimitExceeded;
import com.desafio.tools.management.domain.services.impl.ToolsServiceImpl;
import com.desafio.tools.management.resources.entities.ToolsEntity;
import com.desafio.tools.management.resources.repositories.TagsRepository;
import com.desafio.tools.management.resources.repositories.ToolsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ToolsServiceTest {
    @InjectMocks
    private ToolsServiceImpl service;

    @Mock
    private ToolsRepository toolsRepository;

    @Mock
    private TagsRepository tagsRepository;

    private ToolsEntity toolsEntityWithId;
    private ToolRequest toolRequest;
    private ToolRequest invalidToolRequest;

    @BeforeEach
    void init() {
        toolsEntityWithId = new ToolsEntity(
                1L,
                "tool_request",
                "www.tool_request",
                "tool_request",
                ToolsStatus.CREATED,
                LocalDateTime.now()
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
    void should_create_tool() throws AlreadyExistsException, TagsLimitExceeded {
        when(toolsRepository.existsByTitle(ArgumentMatchers.eq(toolsEntityWithId.getTitle()))).thenReturn(false);

        when(toolsRepository.save(any(ToolsEntity.class))).thenReturn(toolsEntityWithId);

        final var actualTool = service.createTool(toolRequest);

        assertThat(actualTool.getId()).isEqualTo(toolsEntityWithId.getId());
        verify(toolsRepository, times(1)).save(any(ToolsEntity.class));
    }

    @Test
    void should_not_create_tool_when_already_exists() {
        when(toolsRepository.existsByTitle(any(String.class))).thenReturn(true);

        String expectedMessage = "Tool already exists";

        Exception ex = assertThrows(AlreadyExistsException.class, () -> {
            service.createTool(toolRequest);
        });

        assertTrue(ex.getMessage().contains(expectedMessage));
        verify(toolsRepository, times(1)).existsByTitle(any(String.class));
    }

    @Test
    void should_not_create_tool_when_tags_amount_invalid() {
        when(toolsRepository.existsByTitle(ArgumentMatchers.eq(toolsEntityWithId.getTitle()))).thenReturn(false);

        String expectedMessage = "Amount of tags is higher than 8";

        Exception ex = assertThrows(TagsLimitExceeded.class, () -> {
            service.createTool(invalidToolRequest);
        });

        assertTrue(ex.getMessage().contains(expectedMessage));
        verify(toolsRepository, times(1)).existsByTitle(any(String.class));
    }

    @Test
    void should_get_tool_by_id() throws NotFoundException {
        when(toolsRepository.findById(ArgumentMatchers.eq(1L))).thenReturn(Optional.of(toolsEntityWithId));

        final var actualTool = service.getToolById(1L);

        assertThat(actualTool.getId()).isEqualTo(toolsEntityWithId.getId());
        verify(toolsRepository, times(1)).findById(any(Long.class));
    }

    @Test
    void should_not_get_tool_by_id_when_not_found() {
        when(toolsRepository.findById(ArgumentMatchers.eq(1L))).thenReturn(Optional.empty());

        String expectedMessage = "tool does not exist";

        Exception ex = assertThrows(NotFoundException.class, () -> {
            service.getToolById(1L);
        });

        assertTrue(ex.getMessage().contains(expectedMessage));
        verify(toolsRepository, times(1)).findById(any(Long.class));
    }

    @Test
    void should_list_tools() {
        when(toolsRepository.findByStatus()).thenReturn(List.of(toolsEntityWithId));

        List<Tools> tools = service.getTools();

        assertThat(tools.size()).isEqualTo(1);
        verify(toolsRepository, times(1)).findByStatus();
    }

    @Test
    void should_update_tools() throws NotFoundException, TagsLimitExceeded {
        when(toolsRepository.findById(ArgumentMatchers.eq(1L))).thenReturn(Optional.of(toolsEntityWithId));

        when(toolsRepository.save(any(ToolsEntity.class))).thenReturn(toolsEntityWithId);

        final var actualTool = service.updateTool(1L, toolRequest);

        assertThat(actualTool.getId()).isEqualTo(toolsEntityWithId.getId());
        verify(toolsRepository, times(1)).save(any(ToolsEntity.class));
    }

    @Test
    void should_not_update_tools_by_id_when_not_found() {
        when(toolsRepository.findById(ArgumentMatchers.eq(1L))).thenReturn(Optional.empty());

        String expectedMessage = "tool does not exist";

        Exception ex = assertThrows(NotFoundException.class, () -> {
            service.updateTool(1L, toolRequest);
        });

        assertTrue(ex.getMessage().contains(expectedMessage));
        verify(toolsRepository, times(1)).findById(any(Long.class));
    }

    @Test
    void should_not_update_tools_by_id_when_tags_amount_invalid() {
        String expectedMessage = "Amount of tags is higher than 8";

        Exception ex = assertThrows(TagsLimitExceeded.class, () -> {
            service.updateTool(1L, invalidToolRequest);
        });

        assertTrue(ex.getMessage().contains(expectedMessage));
    }

    @Test
    void should_delete_tools() throws NotFoundException {
        when(toolsRepository.findById(ArgumentMatchers.eq(1L))).thenReturn(Optional.of(toolsEntityWithId));

        service.deleteTool(1L);

        verify(toolsRepository, times(1)).findById(any(Long.class));
    }

    @Test
    void should_not_delete_tools_by_id_when_not_found() {
        when(toolsRepository.findById(ArgumentMatchers.eq(1L))).thenReturn(Optional.empty());

        String expectedMessage = "tool does not exist";

        Exception ex = assertThrows(NotFoundException.class, () -> {
            service.deleteTool(1L);
        });

        assertTrue(ex.getMessage().contains(expectedMessage));
        verify(toolsRepository, times(1)).findById(any(Long.class));
    }

}
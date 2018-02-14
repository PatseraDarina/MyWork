package com.epam.autograder.core.mapper.dto;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.epam.autograder.core.dto.InputSourceDto;
import com.epam.autograder.core.dto.SubmissionDto;
import com.epam.autograder.core.service.BaseTest;

import jetbrains.exodus.entitystore.Entity;
import jetbrains.exodus.entitystore.EntityId;

/**
 * Test for EntityToSubmissionMapper
 */
public class EntityToSubmissionTest extends BaseTest {

    @Mock
    private Entity entity;
    @Mock
    private SubmissionDto submissionDto;
    @InjectMocks
    private EntityToSubmissionMapper mapper;

    @BeforeEach
    public void init() {
        when(entity.getId()).thenReturn(mock(EntityId.class));
        when(entity.getProperty("environmentId")).thenReturn("git@git.epam.com:.../...git");
        when(entity.getProperty("inputSource")).thenReturn(InputSourceDto.GIT);
        when(entity.getProperty("inputData")).thenReturn("gcdp_autograder_hello_world");
    }

    @Test
    @DisplayName("Verifies that submission was successful mapped into entity")
    void testMap_Successful() {
        SubmissionDto submission = mapper.map(entity, submissionDto);

        assertNotNull(submission);
    }

    @Test
    @DisplayName("Verifies that submission was not mapped into entity, when submission is null")
    void testMap_WhenSubmissionIsNull() {
        SubmissionDto submission = mapper.map(entity, null);

        assertNull(submission);
    }

    @Test
    @DisplayName("Verifies that submission was not mapped into entity, when entity is null")
    void testMap_WhenEntityIsNull() {
        SubmissionDto submission = mapper.map(null, submissionDto);

        assertNull(submission);
    }
}

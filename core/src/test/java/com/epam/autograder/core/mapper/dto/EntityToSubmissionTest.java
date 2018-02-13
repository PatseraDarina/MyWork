package com.epam.autograder.core.mapper.dto;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.epam.autograder.core.dto.InputSourceDto;
import com.epam.autograder.core.dto.SubmissionDto;
import com.epam.autograder.core.service.BaseTest;

import jetbrains.exodus.entitystore.Entity;
import jetbrains.exodus.entitystore.EntityId;

/**
 * Test for further deleting
 */
public class EntityToSubmissionTest extends BaseTest {

    @Mock
    private Entity entity;
    @Mock
    private SubmissionDto submissionDto;

    @Test
    public void testMap() {
        EntityToSubmissionMapper mapper = new EntityToSubmissionMapper();
        when(entity.getId()).thenReturn(mock(EntityId.class));
        when(entity.getProperty("environmentId")).thenReturn("git@git.epam.com:.../...git");
        when(entity.getProperty("inputSource")).thenReturn(InputSourceDto.GIT);
        when(entity.getProperty("inputData")).thenReturn("gcdp_autograder_hello_world");

        SubmissionDto submission = mapper.map(entity, submissionDto);

        assertNotNull(submission);
    }
}

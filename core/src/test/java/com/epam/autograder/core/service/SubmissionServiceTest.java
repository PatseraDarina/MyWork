package com.epam.autograder.core.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.epam.autograder.core.entity.Submission;
import com.epam.autograder.core.repository.SubmissionRepository;
import com.epam.autograder.core.service.impl.SubmissionServiceImpl;

/**
 * Test class for testing SubmissionService functionality
 *
 * @author Valeriia Chub
 */
public class SubmissionServiceTest extends BaseTest {

    @InjectMocks
    private final SubmissionServiceImpl submissionService = new SubmissionServiceImpl();
    @Mock
    private Submission submission;
    @Mock
    private SubmissionRepository submissionRepository;

    /**
     * Verifies that method save() was invoked
     */
    @Test
    public void shouldInvokeSaveMethod() {
        submissionService.createSubmission(submission);
        verify(submissionRepository, times(1)).save(submission);
    }
}



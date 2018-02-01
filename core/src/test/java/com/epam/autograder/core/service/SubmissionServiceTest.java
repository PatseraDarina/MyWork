package com.epam.autograder.core.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.epam.autograder.core.entity.Submission;
import com.epam.autograder.core.repository.SubmissionRepository;
import com.epam.autograder.core.service.impl.SubmissionServiceImpl;

/**
 * Test class for testing SubmissionService functionality
 *
 * @author Valeriia Chub
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class SubmissionServiceTest {

    @Nested
    @DisplayName("Testing save submission feature")
    class TestSave {

        @InjectMocks
        private final SubmissionServiceImpl submissionService = new SubmissionServiceImpl();
        @Mock
        private Submission submission;
        @Mock
        private SubmissionRepository submissionRepository;

        @Test
        void shouldInvokeSaveMethod() {
            submissionService.createSubmission(submission);
            verify(submissionRepository, times(1)).save(submission);
        }
    }
}


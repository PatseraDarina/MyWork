package com.epam.autograder.core.service;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.epam.autograder.core.entity.Submission;
import com.epam.autograder.core.repository.SubmissionRepository;
import com.epam.autograder.core.service.impl.SubmissionServiceImpl;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SubmissionServiceTest {

    @Nested
    @DisplayName("Test save feature ")
    public class TestSave {

        @InjectMocks
        private final SubmissionServiceImpl submissionService = new SubmissionServiceImpl();
        @Mock
        private Submission submission;
        @Mock
        private SubmissionRepository submissionRepository;

        @Test
        @DisplayName("Test save feature ")
        void shouldInvokeSaveMethod() {
            submissionService.createSubmission(submission);
            verify(submissionRepository).save(submission);
        }
    }
}


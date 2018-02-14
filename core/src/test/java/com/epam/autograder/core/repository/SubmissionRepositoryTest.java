package com.epam.autograder.core.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.epam.autograder.core.dto.InputSourceDto;
import com.epam.autograder.core.dto.SubmissionDto;

/**
 * Test class for repository
 *
 * @author Valeriia Chub
 */
public class SubmissionRepositoryTest extends RepositoryBaseTest {

    private static final String ENVIRONMENT_ID = "gcdp_autograder_hello_world";
    private static final String INPUT_DATA = "git@git.epam.com:.../...git";

    @Nested
    class Save {

        @Test
        @DisplayName("Verifies if the submission was saved with generated id")
        public void ok() {
            SubmissionDto submission = new SubmissionDto();
            submission.setEnvironmentId(ENVIRONMENT_ID);
            submission.setInputSource(InputSourceDto.GIT);
            submission.setInputData(INPUT_DATA);

            SubmissionDto savedSubmission = submissionRepository.save(submission);

            assertEquals(0, savedSubmission.getSubmissionId());
        }

        @Test
        @DisplayName("Verifies that method was not save a submission when it null")
        public void submissionIsNull() {
            SubmissionDto savedSubmission = submissionRepository.save(null);

            assertNull(savedSubmission);
        }
    }
}

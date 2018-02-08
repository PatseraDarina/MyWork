package com.epam.autograder.core.repository;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.epam.autograder.core.entity.InputSource;
import com.epam.autograder.core.entity.Submission;
import com.epam.autograder.core.resource.MockMvcBaseIntegrationTest;

/**
 * Test class for repository
 *
 * @author Valeriia Chub
 */
public class SubmissionRepositoryTest extends MockMvcBaseIntegrationTest {

    private static final String ENVIRONMENT_ID = "gcdp_autograder_hello_world";
    private static final String INPUT_DATA = "git@git.epam.com:.../...git";

    /**
     * Verifies if the submission was saved with generated id
     */
    @Test
    public void testSaveSubmissionOK() {
        Submission submission = new Submission();
        submission.setEnvironmentId(ENVIRONMENT_ID);
        submission.setInputSource(InputSource.GIT);
        submission.setInputData(INPUT_DATA);

        Submission savedSubmission = submissionRepository.save(submission);

        assertEquals(savedSubmission.getSubmissionId(), 0);
    }
}

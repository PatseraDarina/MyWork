package com.epam.autograder.core.repository;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.epam.autograder.core.entity.InputSource;
import com.epam.autograder.core.entity.Submission;

/**
 * Test class for further deleting
 *
 * @author Valeriia Chub
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SubmissionRepositoryTest {

    private static final String ENVIRONMENT_ID = "gcdp_autograder_hello_world";
    private static final String INPUT_DATA = "git@git.epam.com:.../...git";
    @Autowired
    private SubmissionRepository submissionRepository;

    /**
     * Verifies if the submission was saved
     */
    @Test
    public void shouldSaveNewSubmission() {
        Submission submission = new Submission();
        submission.setEnvironmentId(ENVIRONMENT_ID);
        submission.setInputSource(InputSource.GIT);
        submission.setInputData(INPUT_DATA);

        Submission savedSubmission = submissionRepository.save(submission);

        assertNotNull(savedSubmission);
    }
}

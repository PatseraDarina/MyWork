package com.epam.autograder.core.repository;

import static org.junit.Assert.assertEquals;

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

    @Autowired
    private SubmissionRepository submissionRepository;

    /**
     * Verifies if the submission is saved
     */
    @Test
    public void shouldSaveNewSubmission() {
        Submission submission = new Submission();
        submission.setEnvironmentId("gcdp_autograder_hello_world");
        submission.setInputSource(InputSource.GIT);
        submission.setInputData("git@git.epam.com:.../...git");

        Submission savedSubmission = submissionRepository.save(submission);

        assertEquals("Submission id should be equal to 1", savedSubmission.getSubmissionId(), 1);
    }
}

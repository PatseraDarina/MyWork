package com.epam.autograder.core.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.epam.autograder.core.entity.InputSource;
import com.epam.autograder.core.entity.Submission;

/**
 *
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SubmissionRepositoryTest {

    @Autowired
    private SubmissionRepository submissionRepository;

    @Test
    public void shouldSaveNewSubmission() {
        Submission submission = new Submission();
        submission.setEnvironmentId("gcdp_autograder_hello_world");
        submission.setInputSource(InputSource.GIT);
        submission.setInputData("git@git.epam.com:.../...git");

        Submission savedSubmission = submissionRepository.save(submission);

        assertNotNull(savedSubmission);
        assertEquals(savedSubmission.getSubmissionId(), 1);
    }
}

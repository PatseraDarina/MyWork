package com.epam.autograder.runner.service.impl;

import com.epam.autograder.runner.result.Result;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Tests the Stub Service
 */
@RunWith(MockitoJUnitRunner.class)
public class StubDockerServiceTest {

    @Mock
    private Submission submission;
    @InjectMocks
    private StubDockerService dockerService;

    /**
     * Tests the Stub Service
     */
    @Test
    public void runDocker() {
        assertThat(dockerService.runDocker(submission), is(Result.OK));
    }

}

package com.epam.autograder;

import com.epam.autograder.aqa.entity.RunnerSubmission;
import com.epam.autograder.aqa.util.RestClientHelper;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import java.util.concurrent.ThreadLocalRandom;

public class RunnerTestSuite extends BaseTest {

    @Test
    public void test_createContainer_badRequest() {
        HttpResponse response = runnerRestClient.createContainer(null);
        RestClientHelper.verifyStatusCode(response, HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void test_createContainer_OK() {
        long contId = ThreadLocalRandom.current().nextLong(1, 10000);
        RunnerSubmission testSubmission = new RunnerSubmission(
                contId,
                "com.epam.autograder/runner:0.0.1-LOCAL",
                "Hello1"
        );
        HttpResponse response = runnerRestClient.createContainer(testSubmission);
        RestClientHelper.verifyStatusCode(response, HttpStatus.SC_OK);
    }
}

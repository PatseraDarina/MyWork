package com.epam.autograder;

import com.epam.autograder.aqa.entity.RunnerSubmission;
import com.epam.autograder.aqa.util.RestClientHelper;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import java.util.Random;

public class RunnerTestSuite extends BaseTest {

    @Test
    public void test_createContainer_internalServerError() {
        HttpResponse response = runnerRestClient.createContainer(null);
        RestClientHelper.verifyStatusCode(response, HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    public void test_createContainer_OK() {
        long contId = new Random().nextInt(10000) + 1;
        RunnerSubmission testSubmission = new RunnerSubmission(
                contId,
                "com.epam.autograder/runner:0.0.1-LOCAL",
                "Hello1"
        );
        HttpResponse response = runnerRestClient.createContainer(testSubmission);
        RestClientHelper.verifyStatusCode(response, HttpStatus.SC_OK);
    }
}

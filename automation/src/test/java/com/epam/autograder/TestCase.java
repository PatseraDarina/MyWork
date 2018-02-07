package com.epam.autograder;

import com.epam.autograder.aqa.entity.Submission;
import com.epam.autograder.aqa.util.JsonHelper;
import com.epam.autograder.aqa.util.RestClientHelper;
import org.apache.http.HttpResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestCase extends BaseTest {

    @Test
    public void testPostSubmissionWithEmptyRequestBody() {
        int expectedCode = 500;
        Submission testSubmission = null;
        HttpResponse response = coreRestClient.postSubmission(testSubmission);
        RestClientHelper.verifyStatusCode(response, expectedCode);
    }

    @Test
    public void testPostSubmissionWithCorrectRequestBody() {
        Submission testSubmission = new Submission(
                "cdp_autograder_hello_world",
                "GIT",
                "git@git.epam.com:.../...git"
        );
        int expectedCode = 200;
        HttpResponse response = coreRestClient.postSubmission(testSubmission);
        RestClientHelper.verifyStatusCode(response, expectedCode);
        Submission receivedSubmission = JsonHelper.fromJson(RestClientHelper.getContent(response), Submission.class);
        Assert.assertTrue(receivedSubmission.getId() >= 0,
                String.format("FAIL to Verify Submission Id. Actual: '%s', Expected: '>0'", receivedSubmission.getId()));
        Assert.assertEquals(testSubmission.getEnvId(), receivedSubmission.getEnvId(),
                String.format("FAIL to Verify Submission Environment Id. Actual: '%s', Expected: '%s'",
                        testSubmission.getEnvId(), receivedSubmission.getEnvId()));
        Assert.assertEquals(testSubmission.getInputSource(), receivedSubmission.getInputSource(),
                String.format("FAIL to Verify Submission Input Source. Actual: '%s', Expected: '%s'",
                        testSubmission.getInputSource(), receivedSubmission.getInputSource()));
        Assert.assertEquals(testSubmission.getInputData(), receivedSubmission.getInputData(),
                String.format("FAIL to Verify Submission Input Data. Actual: '%s', Expected: '%s'",
                        testSubmission.getInputData(), receivedSubmission.getInputData()));
    }
}

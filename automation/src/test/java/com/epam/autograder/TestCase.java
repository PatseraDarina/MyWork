package com.epam.autograder;

import com.epam.autograder.aqa.entity.Submission;
import com.epam.autograder.aqa.util.JsonHelper;
import com.epam.autograder.aqa.util.RestClientHelper;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestCase extends BaseTest {

    @Test
    public void test_post_submission_with_status_server_error() {
        Submission testSubmission = null;
        HttpResponse response = coreRestClient.postSubmission(testSubmission);
        RestClientHelper.verifyStatusCode(response, HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    public void test_post_submission_status_OK() {
        Submission testSubmission = new Submission(
                "cdp_autograder_hello_world",
                "GIT",
                "git@git.epam.com:.../...git"
        );

        HttpResponse response = coreRestClient.postSubmission(testSubmission);
        RestClientHelper.verifyStatusCode(response, HttpStatus.SC_OK);
        Submission receivedSubmission = JsonHelper.fromJson(RestClientHelper.getContent(response), Submission.class);
        Assert.assertTrue(receivedSubmission.getId() >= 0,
                String.format("FAIL to Verify Submission Id. Actual: '%s', Expected: '>0'", receivedSubmission.getId()));

        HttpResponse response2 = coreRestClient.postSubmission(testSubmission);
        RestClientHelper.verifyStatusCode(response2, HttpStatus.SC_OK);
        Submission receivedSubmission2 = JsonHelper.fromJson(RestClientHelper.getContent(response2), Submission.class);
        Assert.assertTrue(receivedSubmission2.getId() >= 0,
                String.format("FAIL to Verify Submission Id. Actual: '%s', Expected: '>0'", receivedSubmission.getId()));
        Assert.assertNotEquals(receivedSubmission.getId(), receivedSubmission2.getId(),
                String.format("Id is not unique. Actual: '%s', Expected: unique id", receivedSubmission.getId()));
    }
}

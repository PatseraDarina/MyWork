package com.epam.autograder;

import com.epam.autograder.aqa.entity.Submission;
import com.epam.autograder.aqa.util.JsonHelper;
import com.epam.autograder.aqa.util.RestClientHelper;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CoreTestSuite extends BaseTest {

    @Test
    public void test_postSubmission_internalServerError() {
        HttpResponse response = coreRestClient.postSubmission(null);
        RestClientHelper.verifyStatusCode(response, HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    @Test(enabled = false)
    public void test_postSubmission_OK() {
        Submission testSubmission = new Submission(
                "cdp_autograder_hello_world",
                "GIT",
                "git@git.epam.com:.../...git",
                "etre"

        );

        HttpResponse response = coreRestClient.postSubmission(testSubmission);
        RestClientHelper.verifyStatusCode(response, HttpStatus.SC_OK);
        Submission receivedSubmission = JsonHelper.fromJson(RestClientHelper.getContent(response), Submission.class);
        long id = receivedSubmission.getId();
        Assert.assertTrue(id >= 0,
                String.format("Invalid 'Submission' id. Value: %d", id));
    }
}

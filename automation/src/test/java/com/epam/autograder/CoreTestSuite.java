package com.epam.autograder;

import com.epam.autograder.aqa.util.JsonHelper;
import com.epam.autograder.aqa.util.RestClientHelper;
import com.epam.autograder.core.dto.InputSourceDto;
import com.epam.autograder.core.dto.SubmissionDto;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CoreTestSuite extends BaseTest {

    @Test
    public void test_postSubmission_internalServerError() {
        HttpResponse response = coreRestClient.postSubmission(null);
        RestClientHelper.verifyStatusCode(response, HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    public void test_postSubmission_OK() {
        SubmissionDto testSubmission = new SubmissionDto();
        testSubmission.setEnvironmentId("cdp_autograder_hello_world");
        testSubmission.setInputData("git@git.epam.com:.../...git");
        testSubmission.setInputSource(InputSourceDto.GIT);

        HttpResponse response = coreRestClient.postSubmission(testSubmission);
        RestClientHelper.verifyStatusCode(response, HttpStatus.SC_OK);
        SubmissionDto receivedSubmission = JsonHelper.fromJson(RestClientHelper.getContent(response), SubmissionDto.class);
        long id = receivedSubmission.getSubmissionId();
        Assert.assertTrue(id >= 0,
                String.format("Invalid 'Submission' id. Value: %d", id));
    }
}

package com.epam.autograder.aqa.core;

import com.epam.autograder.aqa.annotation.Step;
import com.epam.autograder.aqa.entity.Submission;
import org.apache.http.HttpResponse;

public class CoreRestClient extends RestClient {
    private static final String POST_SUBMISSION = "/submission";
    private static final String GET_SUBMISSION_BY_ID = "/submission/%d";

    public CoreRestClient(String baseUrl) {
        super(baseUrl);
    }

    @Step
    public HttpResponse postSubmission(Submission submission) {
        return post(POST_SUBMISSION, submission);
    }

    @Step
    public HttpResponse getSubmissionById(long id) {
        return get(String.format(GET_SUBMISSION_BY_ID, id));
    }
}

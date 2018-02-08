package com.epam.autograder.aqa.core;

import com.epam.autograder.aqa.entity.RunnerSubmission;
import org.apache.http.HttpResponse;

public class RunnerRestClient extends RestClient {
    private static final String POST_SUBMISSION = "/containers";

    public RunnerRestClient(String baseUrl) {
        super(baseUrl);
    }

    public HttpResponse postSubmission(RunnerSubmission submission) {
        return post(POST_SUBMISSION, submission);
    }
}

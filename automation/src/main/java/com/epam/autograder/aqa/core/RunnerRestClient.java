package com.epam.autograder.aqa.core;

import com.epam.autograder.aqa.entity.RunnerSubmission;
import org.apache.http.HttpResponse;

public class RunnerRestClient extends RestClient {
    private static final String CREATE_CONTAINER = "/containers";

    public RunnerRestClient(String baseUrl) {
        super(baseUrl);
    }

    public HttpResponse createContainer(RunnerSubmission submission) {
        return post(CREATE_CONTAINER, submission);
    }
}

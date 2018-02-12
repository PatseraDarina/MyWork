package com.epam.autograder.aqa.core;

import com.epam.autograder.aqa.annotation.Step;
import com.epam.autograder.runner.entity.Submission;
import org.apache.http.HttpResponse;

public class RunnerRestClient extends RestClient {
    private static final String CREATE_CONTAINER = "/containers";

    public RunnerRestClient(String baseUrl) {
        super(baseUrl);
    }

    @Step
    public HttpResponse createContainer(Submission submission) {
        return post(CREATE_CONTAINER, submission);
    }
}

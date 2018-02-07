package com.epam.autograder.aqa.core;

import com.epam.autograder.aqa.entity.Submission;
import org.apache.http.HttpResponse;

public class CoreRestClient extends RestClient {
    private static final String POST_SUBMISSION = "/submission";

    public CoreRestClient(String baseUrl) {
        super(baseUrl);
    }

    public HttpResponse postSubmission(Submission submission) {
        return post(POST_SUBMISSION, submission);
    }
}

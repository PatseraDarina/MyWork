package com.epam.autograder.runner.service.impl;

import com.epam.autograder.runner.entity.Submission;
import com.epam.autograder.runner.result.Result;
import com.epam.autograder.runner.service.DockerService;

/**
 * Stub Service
 */
public class StubDockerService implements DockerService {
    @Override
    public Result runDocker(Submission submission) {
        return Result.OK;
    }
}

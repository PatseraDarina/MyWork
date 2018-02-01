package com.epam.autograder.runner.service;

import com.epam.autograder.runner.entity.Submission;
import com.epam.autograder.runner.result.Result;

public interface DockerService {
    Result runDocker(Submission submission);
}

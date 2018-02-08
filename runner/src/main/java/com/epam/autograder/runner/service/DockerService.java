package com.epam.autograder.runner.service;

import com.epam.autograder.runner.entity.Submission;
import com.epam.autograder.runner.result.Result;

/**
 * Service which works with Docker
 */
public interface DockerService {
    /**
     * Starts Docker
     *
     * @param submission data which uses for starting docker
     * @return docker status
     */
    Result runDocker(Submission submission);
}

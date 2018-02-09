package com.epam.autograder.runner.service;

import com.epam.autograder.runner.entity.Sandbox;
import com.epam.autograder.runner.entity.SandboxStatus;
import com.epam.autograder.runner.result.Result;


/**
 * Service which works with Docker
 */
public interface DockerService {
    /**
     * Starts Docker
     *
     * @param sandbox data which uses for starting docker
     * @return docker status
     */
    Result runDocker(Sandbox sandbox);

    /**
     * Gets status of the container by {@code id}
     *
     * @param id identifier for getting container status
     * @return container status
     */
    SandboxStatus getStatus(String id);
}

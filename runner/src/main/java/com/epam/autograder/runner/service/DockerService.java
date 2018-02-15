package com.epam.autograder.runner.service;

import com.epam.autograder.runner.entity.Sandbox;
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

//    /**
//     * Gets status of the container by {@code id}
//     *
//     * @param id identifier for getting container status
//     * @return container status
//     */
//    SandboxStatus getState(String id);

//    /**
//     * Gets sandbox object from Json file by specified identifier.
//     *
//     * @param id unique container directory name, that contains "sandbox.json"
//     * @return sandbox object that was parsed from "sandbox.json"
//     */
    Sandbox getSandboxById(String id);
}

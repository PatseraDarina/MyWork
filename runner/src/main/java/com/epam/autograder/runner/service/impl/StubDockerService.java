package com.epam.autograder.runner.service.impl;

import com.epam.autograder.runner.entity.Sandbox;
import com.epam.autograder.runner.entity.SandboxStatus;
import com.epam.autograder.runner.result.Result;
import com.epam.autograder.runner.service.DockerService;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Stub Service
 */
@Service
public class StubDockerService implements DockerService {

    @Override
    public Result runDocker(Sandbox sandbox) {
        return Result.OK;
    }

    @Override
    public SandboxStatus getStatus(UUID id) {
        return null;
    }
}

package com.epam.autograder.runner.service.impl;

import com.epam.autograder.runner.entity.Sandbox;
import com.epam.autograder.runner.entity.SandboxStatus;
import com.epam.autograder.runner.result.Result;
import com.epam.autograder.runner.service.DockerService;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.exception.ConflictException;
import com.github.dockerjava.api.exception.NotFoundException;
import com.github.dockerjava.api.model.Info;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by Andrii_Kasianenko on 2/1/2018.
 */
@Service("dockerServiceImpl")
public class DockerServiceImpl implements DockerService {
    private static final Logger LOGGER = Logger.getLogger(DockerServiceImpl.class);
    private static final String FILE_DIRECTORY = "var/runner/input/payload";
    private static final String FILE_NAME = "payload";

    @Autowired
    @Qualifier("dockerClient")
    private DockerClient dockerClient;

    @Override
    public Result runDocker(Sandbox sandbox) {
        Info info = dockerClient.infoCmd().exec();
        LOGGER.info("DOCKER INFO: " + info);

        String imageName = sandbox.getType();
        try {
            writeToFile(sandbox.getPayload());
            CreateContainerResponse container = dockerClient
                    .createContainerCmd(imageName)
                    .withName(String.valueOf(sandbox.getSubmissionId()))
                    .exec();
            dockerClient.startContainerCmd(container.getId()).exec();
        } catch (NotFoundException | ConflictException e) {
            LOGGER.warn("Exception: " + e);
            return Result.BAD_REQUEST;
        } catch (Exception e) {
            LOGGER.warn("Exception: " + e);
            return Result.INTERNAL_ERROR;
        }
        info = dockerClient.infoCmd().exec();
        LOGGER.info("DOCKER INFO: " + info);
        return Result.OK;
    }

    @Override
    public SandboxStatus getStatus(UUID id) {
        return SandboxStatus.COMPLETE;
    }

    private void writeToFile(String payload) throws IOException {
        File file = new File(FILE_DIRECTORY, FILE_NAME);
        FileUtils.writeStringToFile(file, payload);
    }
}

package com.epam.autograder.runner.service.impl;

import com.epam.autograder.runner.entity.Sandbox;
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
                    .withName(String.valueOf(sandbox.getId()))
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


//    @Override
//    public SandboxStatus getState(String idContainer) {
//        String containerDir = ROOT_DIR + idContainer;
//        if (isExist(containerDir)) {
//            if (isExist(RESULT_FILE)) {
//                try {
//                    if (isStatusOk((State) getJsonObject(RESULT_FILE, State.class))) {
//                        return SandboxStatus.COMPLETE;
//                    }
//                } catch (IOException e) {
//                    LOGGER.warn(e.getMessage(), e);
//                    return SandboxStatus.FAILED;
//                }
//            } else {
//                if (isExist(SANDBOX_FILE)) {
//                    return SandboxStatus.RUNNING;
//                }
//            }
//        }
//        return SandboxStatus.FAILED;
//    }
//
//    @Override
//    public Sandbox getSandbox(String id) {
//        String path = ROOT_DIR + id + SANDBOX_FILE;
//        Sandbox sandbox;
//        try {
//            sandbox = (Sandbox) getJsonObject(path, Sandbox.class);
//        } catch (IOException e) {
//            LOGGER.warn(e.getMessage(), e);
//            sandbox = new Sandbox();
//            sandbox.setStatus(SandboxStatus.FAILED);
//        }
//        return sandbox;
//    }

//    private boolean isExist(String path) {
//        File file = new File(path);
//        return (file.exists());
//    }

    private void writeToFile(String payload) throws IOException {
        File file = new File(FILE_DIRECTORY, FILE_NAME);
        FileUtils.writeStringToFile(file, payload);
    }

//    private State getState(String path) throws IOException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        File file = new File(path);
//        return objectMapper.readValue(file, State.class);
//    }

//    private Object getJsonObject(String path, Class clazz) throws IOException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        File file = new File(path);
//        return objectMapper.readValue(file, clazz);
//    }
}

package com.epam.autograder.runner.service.impl;

import com.epam.autograder.runner.entity.Sandbox;
import com.epam.autograder.runner.entity.SandboxStatus;
import com.epam.autograder.runner.result.Result;
import com.epam.autograder.runner.service.DockerService;
import com.epam.autograder.runner.util.DockerStatusConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.exception.ConflictException;
import com.github.dockerjava.api.exception.NotFoundException;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.Info;
import com.github.dockerjava.api.model.Volume;
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
    private static final String SANDBOX_JSON = "sandbox.json";
    private String fileDirectory;
    private String sandboxJson;
    private static final String FILE_NAME = "payload";
    private String outputPath;
    private String currentPathToProject;

    @Autowired
    @Qualifier("dockerClient")
    private DockerClient dockerClient;
    @Autowired
    @Qualifier("inVolume")
    private Volume inputVolume;

    @Autowired
    @Qualifier("outVolume")
    private Volume outputVolume;
    private Bind inputBind;
    private Bind outputBind;

    @Override
    public Result runDocker(Sandbox sandbox) {
        Info info = dockerClient.infoCmd().exec();
        LOGGER.info("DOCKER INFO: " + info);
        String imageName = sandbox.getType();
        try {
            initPath(sandbox.getId());
            writeToFile(sandbox.getPayload());
            initBinds();
            CreateContainerResponse container = dockerClient
                    .createContainerCmd(imageName)
                    .withVolumes(inputVolume, outputVolume)
                    .withBinds(
                            inputBind, outputBind)
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
        try {
            writeJsonObject(sandbox);
        } catch (IOException e) {
            LOGGER.warn("Exception: " + e);
            return Result.BAD_REQUEST;
        }
        return Result.OK;
    }

    @Override
    public Sandbox getSandboxById(String id) {
        Sandbox sandbox;
        try {
            initPath(id);
            sandbox = getJsonObject();
            sandbox.setStatus(getSandboxStatus(id));
            writeJsonObject(sandbox);
        } catch (IOException e) {
            LOGGER.warn("Exception: " + e);
            return null;
        }
        return sandbox;
    }

    /**
     * @return Sandbox from sandbox.json file
     */
    private Sandbox getJsonObject() throws IOException {
        Sandbox sandbox = new Sandbox();
        File file = new File(sandboxJson + SANDBOX_JSON);
        return new ObjectMapper().readValue(file, sandbox.getClass());
    }

    /**
     * @param sandbox gets from Core request
     * @throws IOException may occur while writing in file
     */
    public void writeJsonObject(Sandbox sandbox) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        if (!new File(sandboxJson).mkdirs()) {
            objectMapper.writeValue(new File(sandboxJson + SANDBOX_JSON), sandbox);
        } else {
            LOGGER.info("Can not write sandbox in file");
        }

    }

    /**
     * @param idContainer gets from Core request
     * @return SandboxStatus current status of container by {@code idContainer}
     */
    private SandboxStatus getSandboxStatus(String idContainer) {
        SandboxStatus status = null;
        try {
            status = DockerStatusConverter.getSandboxStatus(dockerClient.inspectContainerCmd(String.valueOf(idContainer)).exec().getState());
        } catch (NotFoundException e) {
            LOGGER.info("Container not found");
            try {
                return getJsonObject().getStatus();
            } catch (IOException e1) {
                LOGGER.info("Container not found");
                return SandboxStatus.FAILED;
            }
        }
        return status;
    }

    /**
     * @param payload String that get from Submission
     * @throws IOException may occur while writing in file
     */
    private void writeToFile(String payload) throws IOException {
        File payloadFile = new File(fileDirectory, FILE_NAME);
        FileUtils.writeStringToFile(payloadFile, payload);
    }


    /**
     * @throws IOException may occur while directory creating
     */
    private void initBinds() throws IOException {
        File outputDirectory = new File(currentPathToProject + outputPath);
        if (outputDirectory.mkdirs()) {
            inputBind = new Bind(fileDirectory, inputVolume);
            outputBind = new Bind(currentPathToProject + outputPath, outputVolume);
        }
    }

    /**
     * @param idContainer gets from Core request
     */
    private void initPath(String idContainer) {
        try {
            String temp = new File(".").getCanonicalPath();
            currentPathToProject = temp;
            outputPath = File.separator + "var" + File.separator + "runner" + File.separator
                    + idContainer + File.separator + "output" + File.separator;
            fileDirectory = temp + File.separator + "var" + File.separator + "runner" + File.separator
                    + idContainer + File.separator + "input" + File.separator
                    + "payload";
            sandboxJson = temp + File.separator + "var" + File.separator + "runner" + File.separator
                    + idContainer + File.separator;
        } catch (IOException e) {
            LOGGER.info("Path not found");
        }
    }
}

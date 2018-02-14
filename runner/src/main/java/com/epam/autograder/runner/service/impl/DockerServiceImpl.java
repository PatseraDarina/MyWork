package com.epam.autograder.runner.service.impl;

import com.epam.autograder.runner.entity.Submission;
import com.epam.autograder.runner.result.Result;
import com.epam.autograder.runner.service.DockerService;
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
    private static final String FILE_DIRECTORY = File.separator + "var" + File.separator + "runner" + File.separator + "input" + File.separator
            + "payload";
    private static final String FILE_NAME = "payload";
    private static final String OUTPUT_PATH = File.separator + "var" + File.separator + "runner" + File.separator + "output" + File.separator;

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
    public Result runDocker(Submission submission) {
        Info info = dockerClient.infoCmd().exec();
        LOGGER.info("DOCKER INFO: " + info);
        String imageName = submission.getEnvironmentId();
        try {
            writeToFile(submission.getPayload());
            initBinds();
            CreateContainerResponse container = dockerClient
                    .createContainerCmd(imageName).withVolumes(inputVolume, outputVolume)
                    .withBinds(
                            inputBind, outputBind)
                    .withName(String.valueOf(submission.getSubmissionId()))
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

    /**
     * @param payload String that get from Submission
     * @throws IOException may occur while writing in file
     */
    private void writeToFile(String payload) throws IOException {
        currentPathToProject = new java.io.File(".").getCanonicalPath();
        File payloadFile = new File(currentPathToProject + FILE_DIRECTORY, FILE_NAME);
        FileUtils.writeStringToFile(payloadFile, payload);
    }

    /**
     * @throws IOException may occur while directory creating
     */
    private void initBinds() throws IOException {
        File outputDirectory = new File(currentPathToProject + OUTPUT_PATH);
        if (outputDirectory.mkdirs()) {
            inputBind = new Bind(currentPathToProject + FILE_DIRECTORY, inputVolume);
            outputBind = new Bind(currentPathToProject + OUTPUT_PATH, outputVolume);
        }
    }

}

package com.epam.autograder.runner.service.impl;

import com.epam.autograder.runner.entity.Submission;
import com.epam.autograder.runner.result.Result;
import com.epam.autograder.runner.service.DockerService;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.exception.NotFoundException;
import com.github.dockerjava.api.model.Info;
import com.github.dockerjava.core.command.WaitContainerResultCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by Andrii_Kasianenko on 2/1/2018.
 */
@Service("dockerServiceImpl")
public class DockerServiceImpl implements DockerService {

    @Autowired
    @Qualifier("dockerClient")
    private DockerClient dockerClient;

    @Override
    public Result runDocker(Submission submission) {
        Info info = dockerClient.infoCmd().exec();
        System.out.println("DOCKER INFO: " + info);
        String imageName = submission.getEnvironmentId();
        try {
            CreateContainerResponse container = dockerClient
                    .createContainerCmd(imageName)
                    .exec();

            dockerClient.startContainerCmd(container.getId()).exec();
            dockerClient.waitContainerCmd(container.getId()).exec((new WaitContainerResultCallback()));
            dockerClient.stopContainerCmd(container.getId()).exec();
        } catch (NotFoundException e) {
            return Result.ERROR;
        }

        info = dockerClient.infoCmd().exec();
        System.out.println("DOCKER INFO: " + info);

        return Result.OK;
    }
}

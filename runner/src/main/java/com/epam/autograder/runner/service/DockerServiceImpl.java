package com.epam.autograder.runner.service;

import com.epam.autograder.runner.entity.Submission;
import com.epam.autograder.runner.result.Result;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.Info;
import com.github.dockerjava.api.model.Ports;
import com.github.dockerjava.api.model.SearchItem;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * Created by Andrii_Kasianenko on 2/1/2018.
 */
@Service("dockerServiceImpl")
public class DockerServiceImpl implements DockerService {

    private static final String IMAGE_NAME = "busybox";

    @Autowired
    @Qualifier("dockerClient")
    private DockerClient dockerClient;

    public DockerServiceImpl() {
    }

    @Override
    public Result runDocker(Submission submission) {
        Info info = dockerClient.infoCmd().exec();
        System.out.println("DOCKER INFO: "  + info);

        CreateContainerResponse container = dockerClient
                .createContainerCmd(IMAGE_NAME)
                .exec();

        dockerClient.startContainerCmd(container.getId()).exec();

        info = dockerClient.infoCmd().exec();
        System.out.println("DOCKER INFO: "  + info);

        return null;
    }
}

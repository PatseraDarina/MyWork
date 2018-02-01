package com.epam.autograder.runner.config;

import com.epam.autograder.runner.service.DockerService;
import com.epam.autograder.runner.service.DockerServiceImpl;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DockerClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Andrii_Kasianenko on 2/1/2018.
 */
@Configuration
public class RunnerConfig {

    @Bean
    public DockerClient dockerClient() {
        return DockerClientBuilder.getInstance("tcp://localhost:2375").build();
    }

    @Bean
    public DockerService dockerService() {
        return new DockerServiceImpl();
    }
}

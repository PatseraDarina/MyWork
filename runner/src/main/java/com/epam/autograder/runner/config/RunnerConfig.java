package com.epam.autograder.runner.config;

import com.epam.autograder.runner.service.DockerService;
import com.epam.autograder.runner.service.impl.DockerServiceImpl;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DockerClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Andrii_Kasianenko on 2/1/2018.
 */
@Configuration
public class RunnerConfig {
    /**
     * @return DockerClient bean
     */
    @Bean
    public DockerClient dockerClient() {
        return DockerClientBuilder.getInstance("tcp://localhost:2375").build();
    }

    /**
     * @return DockerService Bean
     */
    @Bean
    public DockerService dockerService() {
        return new DockerServiceImpl();
    }
}

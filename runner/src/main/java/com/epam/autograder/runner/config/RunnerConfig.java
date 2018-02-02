package com.epam.autograder.runner.config;

import com.epam.autograder.runner.service.DockerService;
import com.epam.autograder.runner.service.impl.DockerServiceImpl;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DockerClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * Created by Andrii_Kasianenko on 2/1/2018.
 */
@Configuration
@PropertySource("classpath:docker-java.properties")
public class RunnerConfig {

    private static final String DOCKER_HOST = "DOCKER_HOST";

    @Autowired
    private Environment environment;

    /**
     * @return DockerClient bean
     */
    @Bean
    public DockerClient dockerClient() {
        return DockerClientBuilder.getInstance(environment
                .getRequiredProperty(DOCKER_HOST))
                .build();
    }

    /**
     * @return DockerService Bean
     */
    @Bean
    public DockerService dockerService() {
        return new DockerServiceImpl();
    }
}

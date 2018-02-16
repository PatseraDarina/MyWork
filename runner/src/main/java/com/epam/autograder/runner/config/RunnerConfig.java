package com.epam.autograder.runner.config;

import com.epam.autograder.runner.result.Result;
import com.epam.autograder.runner.service.DockerService;
import com.epam.autograder.runner.service.impl.DockerServiceImpl;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Volume;
import com.github.dockerjava.core.DockerClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import java.util.HashMap;
import java.util.Map;

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

    /**
     * @return HttpStatusMap Bean
     */
    @Bean
    public Map<Result, HttpStatus> initHttpStatusMap() {
        Map<Result, HttpStatus> status = new HashMap<>();
        status.put(Result.OK, HttpStatus.OK);
        status.put(Result.BAD_REQUEST, HttpStatus.BAD_REQUEST);
        status.put(Result.INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        return status;
    }

    /**
     * @return MethodValidationPostProcessor Bean
     */
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

    /**
     * @param path to inputFolder in Docker
     * @return Volume
     */
    @Bean
    public Volume inVolume(@Value("${volume.input}") String path) {
        return new Volume(path);
    }

    /**
     * @param path to outputFolder in Docker
     * @return Volume
     */
    @Bean
    public Volume outVolume(@Value("${volume.output}") String path) {
        return new Volume(path);
    }

}

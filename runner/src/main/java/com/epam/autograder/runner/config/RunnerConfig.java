package com.epam.autograder.runner.config;

import com.epam.autograder.runner.result.Result;
import com.epam.autograder.runner.service.DockerService;
import com.epam.autograder.runner.service.impl.DockerServiceImpl;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.Volume;
import com.github.dockerjava.core.DockerClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Andrii_Kasianenko on 2/1/2018.
 */
@Configuration
@PropertySource("classpath:docker-java.properties")
public class RunnerConfig {

    private static final String DOCKER_HOST = "DOCKER_HOST";
    private static final String INPUT_PAYLOAD_PATH = "D:" + File.separator + "AutoGrader_Winter" + File.separator
            + "EPM-RDK1-AutoGrader"
            + File.separator + "var" + File.separator + "runner" + File.separator + "input" + File.separator
            + "payload" + File.separator + "payload";
    private static final String OUTPUT_PATH = "D:" + File.separator + "AutoGrader_Winter" + File.separator
            + "EPM-RDK1-AutoGrader" + File.separator + "var" + File.separator + "runner" + File.separator + "output";

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

    /**
     * @return Bind
     */
    @Bean
    public Bind inBind() {
        return new Bind(INPUT_PAYLOAD_PATH, inVolume(null));
    }

    /**
     * @return Bind
     */
    @Bean
    public Bind outBind() {
        return new Bind(OUTPUT_PATH, outVolume(null));
    }
}

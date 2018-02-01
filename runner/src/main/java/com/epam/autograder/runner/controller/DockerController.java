package com.epam.autograder.runner.controller;

import com.epam.autograder.runner.entity.Submission;
import com.epam.autograder.runner.result.Result;
import com.epam.autograder.runner.service.DockerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create docker container.
 */
@RestController
public class DockerController {
    /**
     * @param dockerService contains 'runDocker' method
     */
    @Autowired
    @Qualifier("dockerServiceImpl")
    private DockerService dockerService;
    /**
     * @return  ResponseEntity<Result> in JSON format
     * @param submission get from Core
     */
    @PostMapping("/container/{containerName}")
    public ResponseEntity<Result> createContainer(@RequestBody Submission submission) {
        Result result = dockerService.runDocker(submission);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}

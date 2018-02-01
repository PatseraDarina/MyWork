package com.epam.autograder.runner.resource;

import com.epam.autograder.runner.entity.Submission;
import com.epam.autograder.runner.result.Result;
import com.epam.autograder.runner.service.DockerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create docker container.
 */
@RestController
public class DockerResource {
    @Autowired
    private DockerService dockerService;

    @PostMapping("/container/{containerName}")
    public ResponseEntity<Result> createContainer(@RequestBody Submission submission) {
        Result result = dockerService.runDocker(submission);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}

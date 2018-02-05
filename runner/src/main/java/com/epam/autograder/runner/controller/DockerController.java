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

import java.util.Map;

/**
 * Create docker container.
 */
@RestController
public class DockerController {
    @Autowired
    @Qualifier("initHttpStatusMap")
    private Map<Result, HttpStatus> statusMap;
    @Autowired
    @Qualifier("dockerServiceImpl")
    private DockerService dockerService;

    /**
     * @param submission get from Core
     * @return ResponseEntity<Result> in JSON format
     */
    @PostMapping("/containers")
    public ResponseEntity<?> createContainer(@RequestBody Submission submission) {
        Result result = dockerService.runDocker(submission);
        return new ResponseEntity<>(statusMap.get(result));
    }
}

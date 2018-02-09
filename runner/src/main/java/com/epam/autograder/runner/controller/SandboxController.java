package com.epam.autograder.runner.controller;

import com.epam.autograder.runner.entity.Sandbox;
import com.epam.autograder.runner.entity.SandboxStatus;
import com.epam.autograder.runner.result.Result;
import com.epam.autograder.runner.service.DockerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

/**
 * Create docker container.
 */
@RestController
public class SandboxController {
    @Autowired
    @Qualifier("initHttpStatusMap")
    private Map<Result, HttpStatus> statusMap;
    @Autowired
    @Qualifier("dockerServiceImpl")
    private DockerService dockerService;

    /**
     * @param sandbox get from Core
     * @return ResponseEntity<>(HttpStatus) in JSON format
     */
    @PostMapping("/sandboxes")
    public ResponseEntity<?> acceptSandbox(@RequestBody Sandbox sandbox) {
        Result result = dockerService.runDocker(sandbox);
        return new ResponseEntity<>(statusMap.get(result));
    }

    @GetMapping("/sandboxes/{id}")
    public SandboxStatus getContainerStatus(@PathVariable String id) {
        return dockerService.getStatus(UUID.fromString(id));
    }

}

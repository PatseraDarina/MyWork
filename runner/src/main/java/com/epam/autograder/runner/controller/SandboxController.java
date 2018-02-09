package com.epam.autograder.runner.controller;

import com.epam.autograder.runner.entity.Sandbox;
import com.epam.autograder.runner.result.Result;
import com.epam.autograder.runner.service.DockerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Map;

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
     * @return should return ResponseEntity<Sandbox> in JSON format
     */
    @PostMapping("/sandboxes")
    public ResponseEntity<?> acceptSandbox(@RequestBody Sandbox sandbox) {
        Result result = dockerService.runDocker(sandbox);
        return new ResponseEntity<>(statusMap.get(result));
    }
    /**
     * @param id get from Core
     * @return SandboxStatus
     */
    @GetMapping("/sandboxes/{id}")
    public Sandbox getContainerStatus(@PathVariable String id) {
        //SandboxStatus status = dockerService.getStatus(id);
        return new Sandbox();
    }
}

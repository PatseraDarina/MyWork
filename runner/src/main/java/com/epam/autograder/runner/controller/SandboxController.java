package com.epam.autograder.runner.controller;

import com.epam.autograder.runner.entity.Sandbox;
import com.epam.autograder.runner.entity.SandboxStatus;
import com.epam.autograder.runner.result.Result;
import com.epam.autograder.runner.service.DockerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Create docker container.
 */
@RestController
@Validated
public class SandboxController {
    @Autowired
    @Qualifier("initHttpStatusMap")
    private Map<Result, HttpStatus> statusMap;
    @Autowired
    @Qualifier("dockerServiceImpl")
    private DockerService dockerService;

    /**
     * @param sandbox       get from Core
     * @param bindingResult contains mastakes of validator
     * @return should return ResponseEntity<Sandbox> in JSON format
     */
    @PostMapping("/sandboxes")
    public ResponseEntity<?> acceptSandbox(@Valid @RequestBody Sandbox sandbox, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Result result = dockerService.runDocker(sandbox);
        return new ResponseEntity<>(statusMap.get(result));
    }

    /**
     * @param id get from Core
     * @return ResponseEntity<Sandbox>
     */
    @GetMapping("/sandboxes/{id}")
    public ResponseEntity<?> getContainerStatus(@Pattern(regexp = "^[\\w\\d\\-]+$", message = "Wrong id format") @PathVariable String id) {
        Sandbox sandbox = dockerService.getSandboxById(id);
        if (Objects.isNull(sandbox)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(sandbox, HttpStatus.OK);
    }

    /**
     * @param e ConstraintViolationException
     * @return exception massage
     */
    @ExceptionHandler(value = {ConstraintViolationException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public String handle(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        StringBuilder strBuilder = new StringBuilder();
        for (ConstraintViolation<?> violation : violations) {
            strBuilder.append(violation.getMessage() + "\n");
        }
        return strBuilder.toString();
    }
}


package com.epam.autograder.core.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.epam.autograder.core.dto.SubmissionDto;
import com.epam.autograder.core.service.SubmissionService;

/**
 * Rest controller for submission resource
 *
 * @author Valeriia Chub
 */
@RestController
public class SubmissionResource {

    @Autowired
    private SubmissionService submissionService;

    /**
     * Endpoint which gets a Submission object, saves it
     * in DB and returns it with generated id.
     *
     * @param submission a submission without id
     * @return a submission with generated id
     */
    @PostMapping("/submission")
    public SubmissionDto createSubmission(@RequestBody SubmissionDto submission) {
        return submissionService.createSubmission(submission);
    }
}

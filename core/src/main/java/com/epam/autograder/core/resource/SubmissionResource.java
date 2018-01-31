package com.epam.autograder.core.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.epam.autograder.core.entity.Submission;
import com.epam.autograder.core.service.SubmissionService;

@RestController
public class SubmissionResource {

    @Autowired
    private SubmissionService submissionService;

    @PostMapping("/submission")
    public Submission createSubmission(@RequestBody Submission submission) {
        return submissionService.createSubmission(submission);
    }
}

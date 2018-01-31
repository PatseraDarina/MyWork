package com.epam.autograder.core.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.epam.autograder.core.entity.Submission;
import com.epam.autograder.core.enums.InputSource;
import com.epam.autograder.core.service.SubmissionService;

@RestController
public class SubmissionResource {

    @Autowired
    private SubmissionService submissionService;

    @RequestMapping(method = RequestMethod.POST, value = "/submission")
    public Submission createSubmission(@RequestBody Submission submission) {
        submissionService.createSubmission(submission);

        return submission;
    }
}

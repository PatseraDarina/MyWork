package com.epam.autograder.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.autograder.core.entity.Submission;
import com.epam.autograder.core.repository.SubmissionRepository;
import com.epam.autograder.core.service.SubmissionService;

@Service
public class SubmissionServiceImpl implements SubmissionService {
    /* @Autowired
     public void setSubmissionRepository(SubmissionRepository submissionRepository) {
         this.submissionRepository = submissionRepository;
     }*/
    @Autowired
    private SubmissionRepository submissionRepository;

    @Override
    public Submission createSubmission(Submission submission) {
        return submissionRepository.save(submission);
    }
}

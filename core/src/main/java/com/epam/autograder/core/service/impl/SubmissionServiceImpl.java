package com.epam.autograder.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.autograder.core.dto.SubmissionDto;
import com.epam.autograder.core.repository.SubmissionRepository;
import com.epam.autograder.core.service.SubmissionService;

/**
 * Implementation for the Submission service.
 *
 * @author Valeriia Chub
 */
@Service
public class SubmissionServiceImpl implements SubmissionService {

    @Autowired
    private SubmissionRepository submissionRepository;

    /**
     * @param submission a submission without id
     * @return a submission with generated id
     */
    @Override
    public SubmissionDto createSubmission(SubmissionDto submission) {
        return submissionRepository.save(submission);
    }
}

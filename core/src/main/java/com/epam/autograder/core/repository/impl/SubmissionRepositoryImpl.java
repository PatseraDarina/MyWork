package com.epam.autograder.core.repository.impl;

import org.springframework.stereotype.Repository;

import com.epam.autograder.core.entity.Submission;
import com.epam.autograder.core.repository.SubmissionRepository;

/**
 * Implementation for the Submission repository.
 *
 * @author Valeriia Chub
 */
@Repository
public class SubmissionRepositoryImpl implements SubmissionRepository {

    @Override
    public Submission save(Submission submission) {
        return null;
    }
}

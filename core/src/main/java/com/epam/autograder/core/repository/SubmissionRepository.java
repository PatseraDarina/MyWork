package com.epam.autograder.core.repository;

import com.epam.autograder.core.entity.Submission;

/**
 * Interface for CRUD operations on a repository for a Submission.
 *
 * @author Valeriia Chub
 */

public interface SubmissionRepository {

    /**
     * @param submission a submission without id
     * @return a submission with generated id
     */
    Submission save(Submission submission);
}

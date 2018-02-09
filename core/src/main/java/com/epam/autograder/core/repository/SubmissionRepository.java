package com.epam.autograder.core.repository;

import com.epam.autograder.core.dto.SubmissionDto;

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
    SubmissionDto save(SubmissionDto submission);
}

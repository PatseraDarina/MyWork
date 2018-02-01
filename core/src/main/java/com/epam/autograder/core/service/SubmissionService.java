package com.epam.autograder.core.service;

import com.epam.autograder.core.entity.Submission;

/**
 * Interface for the Submission service.
 *
 * @author Valeriia Chub
 */
public interface SubmissionService {

   /**
    * @param submission a submission without id
    * @return a submission with set up id
    */
   Submission createSubmission(Submission submission);
}

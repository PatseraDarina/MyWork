package com.epam.autograder.core.repository;

import org.springframework.data.repository.CrudRepository;

import com.epam.autograder.core.entity.Submission;
/**
 * Interface for CRUD operations on a repository for a Submission.
 *
 * @author Valeriia Chub
 */
public interface SubmissionRepository extends CrudRepository<Submission, Long> {

}

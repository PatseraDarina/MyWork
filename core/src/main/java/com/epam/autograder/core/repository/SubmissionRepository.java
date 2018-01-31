package com.epam.autograder.core.repository;

import org.springframework.data.repository.CrudRepository;

import com.epam.autograder.core.entity.Submission;

public interface SubmissionRepository extends CrudRepository<Submission, Long> {

}

package com.epam.autograder.core.mapper;

import org.springframework.stereotype.Component;

import com.epam.autograder.core.entity.Submission;

import jetbrains.exodus.entitystore.Entity;
import jetbrains.exodus.entitystore.StoreTransaction;

/**
 * Mapper for submission
 */
@Component
public class SubmissionMapper {

    private static final String SUBMISSION_ENTITY_NAME = "Submission";
    private static final String ENVIRONMENT_ID_PROPERTY = "environmentId";
    private static final String INPUT_SOURCE_PROPERTY = "inputSource";
    private static final String INPUT_DATA_PROPERTY = "inputData";

    /**
     * Transform submission to entity
     *
     * @param submission  submission
     * @param transaction transaction
     * @return entity
     */
    public Entity submissionToEntity(Submission submission, StoreTransaction transaction) {
        Entity submissionEntity = transaction.newEntity(SUBMISSION_ENTITY_NAME);

        submissionEntity.setProperty(ENVIRONMENT_ID_PROPERTY, submission.getEnvironmentId());
        submissionEntity.setProperty(INPUT_SOURCE_PROPERTY, submission.getInputSource().toString());
        submissionEntity.setProperty(INPUT_DATA_PROPERTY, submission.getInputData());

        return submissionEntity;
    }
}

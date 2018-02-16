package com.epam.autograder.core.mapper.entity;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.epam.autograder.core.dto.SubmissionDto;
import com.epam.autograder.core.mapper.Mapper;

import jetbrains.exodus.entitystore.Entity;

/**
 * Maps Submission to DB Entity
 */
@Component
public class SubmissionToEntityMapper implements Mapper<SubmissionDto, Entity> {

    private static final String ENVIRONMENT_ID_PROPERTY = "environmentId";
    private static final String INPUT_SOURCE_PROPERTY = "inputSource";
    private static final String INPUT_DATA_PROPERTY = "inputData";

    @Override
    public Entity map(SubmissionDto submission, Entity submissionEntity) {
        if (Objects.nonNull(submissionEntity) && Objects.nonNull(submission)) {
            submissionEntity.setProperty(ENVIRONMENT_ID_PROPERTY, submission.getEnvironmentId());
            submissionEntity.setProperty(INPUT_SOURCE_PROPERTY, submission.getInputSource().toString());
            submissionEntity.setProperty(INPUT_DATA_PROPERTY, submission.getInputData());

            return submissionEntity;
        }
        return null;
    }
}

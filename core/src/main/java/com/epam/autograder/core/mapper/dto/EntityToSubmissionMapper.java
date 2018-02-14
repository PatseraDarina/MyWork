package com.epam.autograder.core.mapper.dto;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.epam.autograder.core.dto.InputSourceDto;
import com.epam.autograder.core.dto.SubmissionDto;
import com.epam.autograder.core.mapper.Mapper;

import jetbrains.exodus.entitystore.Entity;

/**
 * Maps DB Entity to Submission
 */
@Component
public class EntityToSubmissionMapper implements Mapper<Entity, SubmissionDto> {

    private static final String ENVIRONMENT_ID_PROPERTY = "environmentId";
    private static final String INPUT_SOURCE_PROPERTY = "inputSource";
    private static final String INPUT_DATA_PROPERTY = "inputData";

    @Override
    public SubmissionDto map(Entity entity, SubmissionDto submission) {
        if (Objects.nonNull(entity) && Objects.nonNull(submission)) {
            submission.setSubmissionId(entity.getId().getLocalId());
            submission.setEnvironmentId(String.valueOf(entity.getProperty(ENVIRONMENT_ID_PROPERTY)));

            String inputSource = String.valueOf(entity.getProperty(INPUT_SOURCE_PROPERTY));
            submission.setInputSource(InputSourceDto.valueOf(inputSource));

            submission.setInputData(String.valueOf(entity.getProperty(INPUT_DATA_PROPERTY)));
            return submission;
        }
        return null;
    }
}

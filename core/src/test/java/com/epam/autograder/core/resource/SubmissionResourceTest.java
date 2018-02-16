package com.epam.autograder.core.resource;

import com.epam.autograder.core.dto.InputSourceDto;
import com.epam.autograder.core.dto.SubmissionDto;
import com.epam.autograder.core.exception.BusinessException;
import com.epam.autograder.core.service.SubmissionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.epam.autograder.core.resource.ExceptionHandlingController.DEFAULT_ERROR_DESCRIPTION;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class SubmissionResourceTest extends MockMvcBaseIntegrationTest {
    private static final String SUBMISSION_URL = "/submission";
    private static final String ENVIRONMENT_ID_FIELD = "$.environmentId";
    private static final String INPUT_SOURCE_FIELD = "$.inputSource";
    private static final String INPUT_DATA_FIELD = "$.inputData";
    private static final String STATUS_CODE_FIELD = "$.statusCode";
    private static final String STATUS_NAME_FIELD = "$.statusName";
    private static final String DESCRIPTION_FIELD = "$.description";
    private static final String CHECK_PARAMETERS_MSG = "Check parameters";

    private SubmissionDto submission;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private SubmissionService submissionService;

    @BeforeEach
    public void init() {
        submission = new SubmissionDto();
        submission.setInputSource(InputSourceDto.GIT);
        submission.setInputData("git@git.epam.com:.../...git");
        submission.setEnvironmentId("gcdp_autograder_hello_world");
    }

    @Nested
    class CreateSubmission {
        @Test
        public void statusOk() throws Exception {
            when(submissionService.createSubmission(any(SubmissionDto.class)))
                    .thenReturn(submission);

            mockMvc.perform(post(SUBMISSION_URL)
                    .content(objectMapper.writeValueAsString(submission))
                    .contentType(APPLICATION_JSON_UTF8))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath(ENVIRONMENT_ID_FIELD, is(submission.getEnvironmentId())))
                    .andExpect(jsonPath(INPUT_SOURCE_FIELD, is(submission.getInputSource().name())))
                    .andExpect(jsonPath(INPUT_DATA_FIELD, is(submission.getInputData())));
        }

        @Test
        public void statusBadRequest() throws Exception {
            when(submissionService.createSubmission(any(SubmissionDto.class)))
                    .thenThrow(new BusinessException(CHECK_PARAMETERS_MSG));

            mockMvc.perform(post(SUBMISSION_URL)
                    .content(objectMapper.writeValueAsString(submission))
                    .contentType(APPLICATION_JSON_UTF8))
                    .andExpect(status().is4xxClientError())
                    .andExpect(jsonPath(STATUS_CODE_FIELD, is(BAD_REQUEST.value())))
                    .andExpect(jsonPath(STATUS_NAME_FIELD, is(BAD_REQUEST.name())))
                    .andExpect(jsonPath(DESCRIPTION_FIELD, is(CHECK_PARAMETERS_MSG)));
        }

        @Test
        public void statusInternalServerError() throws Exception {
            when(submissionService.createSubmission(any(SubmissionDto.class)))
                    .thenThrow(new RuntimeException(DEFAULT_ERROR_DESCRIPTION));

            mockMvc.perform(post(SUBMISSION_URL)
                    .content(objectMapper.writeValueAsString(submission))
                    .contentType(APPLICATION_JSON_UTF8))
                    .andExpect(status().is5xxServerError())
                    .andExpect(jsonPath(STATUS_CODE_FIELD, is(INTERNAL_SERVER_ERROR.value())))
                    .andExpect(jsonPath(STATUS_NAME_FIELD, is(INTERNAL_SERVER_ERROR.name())))
                    .andExpect(jsonPath(DESCRIPTION_FIELD, is(DEFAULT_ERROR_DESCRIPTION)));
        }
    }

}

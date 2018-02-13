package com.epam.autograder.core.resource;

import com.epam.autograder.core.dto.InputSourceDto;
import com.epam.autograder.core.dto.SubmissionDto;
import com.epam.autograder.core.exception.BusinessException;
import com.epam.autograder.core.repository.SubmissionRepository;
import com.epam.autograder.core.service.SubmissionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.nio.charset.Charset;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for testing SubmissionResource functionality
 * @see ExceptionHandlingController
 */

public class SubmissionResourceTest extends MockMvcBaseIntegrationTest {
    private static final String URL_TEMPLATE = "/submission";
    private static final String ENVIRONMENT_ID = "environmentId";
    private static final String INPUT_SOURCE = "inputSource";
    private static final String INPUT_DATA = "inputData";
    private static final String ENVIRONMENT_ID_VALUE = "gcdp_autograder_hello_world";
    private static final String INPUT_SOURCE_VALUE = "GIT";
    private static final String INPUT_DATA_VALUE = "git@git.epam.com:.../...git";
    private static final String STATUS_CODE_FIELD = "$.statusCode";
    private static final String STATUS_NAME_FIELD = "$.statusName";
    private static final String DESCRIPTION_FIELD = "$.description";
    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private SubmissionDto submission;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private SubmissionRepository submissionRepository;
    @Autowired
    private SubmissionService submissionService;

    @BeforeEach
    public void init() {
        submission = new SubmissionDto();
        submission.setInputSource(InputSourceDto.GIT);
        submission.setInputData(INPUT_DATA_VALUE);
        submission.setEnvironmentId(ENVIRONMENT_ID_VALUE);
    }

    /**
     * Test successful status of POST
     *
     * @throws Exception if a problem occurs
     */
    @Test
    public void testCreateSubmission_Ok() throws Exception {
        when(submissionService.createSubmission(any(SubmissionDto.class)))
                .thenReturn(submissionRepository.save(submission));
        mockMvc.perform(post(URL_TEMPLATE)
                .content(objectMapper.writeValueAsString(submission))
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath(ENVIRONMENT_ID, is(ENVIRONMENT_ID_VALUE)))
                .andExpect(jsonPath(INPUT_SOURCE, is(INPUT_SOURCE_VALUE)))
                .andExpect(jsonPath(INPUT_DATA, is(INPUT_DATA_VALUE)))
                .andExpect(status().isOk());
    }

    /**
     * Test client error of POST
     *
     * @throws Exception if a problem occurs
     */
    @Test
    public void testCreateSubmission_BusinessException() throws Exception {
        HttpStatus expectedHttpStatus = HttpStatus.BAD_REQUEST;
        String expectedDescription = "BusinessException";
        when(submissionService.createSubmission(any(SubmissionDto.class)))
                .thenThrow(new BusinessException(expectedDescription));
        mockMvc.perform(post(URL_TEMPLATE)
                .content(objectMapper.writeValueAsString(submission))
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath(STATUS_CODE_FIELD, Is.is(expectedHttpStatus.value())))
                .andExpect(jsonPath(STATUS_NAME_FIELD, Is.is(expectedHttpStatus.name())))
                .andExpect(jsonPath(DESCRIPTION_FIELD, Is.is(expectedDescription)));
    }

    /**
     * Test server error of POST
     *
     * @throws Exception if a problem occurs
     */
    @Test
    public void testCreateSubmission_RuntimeException() throws Exception {
        HttpStatus expectedHttpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        String expectedDescription = "RuntimeException";
        when(submissionService.createSubmission(any(SubmissionDto.class)))
                .thenThrow(new RuntimeException(expectedDescription));
        mockMvc.perform(post(URL_TEMPLATE)
                .content(objectMapper.writeValueAsString(submission))
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath(STATUS_CODE_FIELD, Is.is(expectedHttpStatus.value())))
                .andExpect(jsonPath(STATUS_NAME_FIELD, Is.is(expectedHttpStatus.name())))
                .andExpect(jsonPath(DESCRIPTION_FIELD, Is.is(expectedDescription)));
    }
}

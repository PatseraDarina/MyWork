package com.epam.autograder.core.exception.controller;

import com.epam.autograder.core.dto.SubmissionDto;
import com.epam.autograder.core.exception.BusinessException;
import com.epam.autograder.core.resource.ExceptionHandlingController;
import com.epam.autograder.core.resource.SubmissionResource;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.Charset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Class tests exception handling in application
 *
 * @author Eduard Khachirov
 * @see ExceptionHandlingController
 */
@SpringBootTest
@ExtendWith({SpringExtension.class})
public class ExceptionHandlingControllerTest {

    private static final String STATUS_CODE_FIELD = "$.statusCode";
    private static final String STATUS_NAME_FIELD = "$.statusName";
    private static final String DESCRIPTION_FIELD = "$.description";
    private static final String REQUEST_URL = "/submission";
    private String submissionJson;
    private MediaType jsonMediaType;

    @Autowired
    private ExceptionHandlingController exceptionHandlingController;

    @Mock
    private SubmissionResource stubController;

    private MockMvc mockMvc;

    /**
     * Sets up resources for testing
     *
     * @throws JsonProcessingException throws when can not parse Submission to json
     */
    @BeforeEach
    public void setUp() throws JsonProcessingException {
        mockMvc = MockMvcBuilders
                .standaloneSetup(stubController)
                .setControllerAdvice(exceptionHandlingController)
                .build();
        submissionJson = new ObjectMapper().writeValueAsString(new SubmissionDto());
        jsonMediaType = new MediaType(MediaType.APPLICATION_JSON, Charset.forName("utf8"));
    }

    /**
     * Tests case when BusinessException occurs
     *
     * @throws Exception throws exception
     */
    @Test
    public void shouldReturnBadRequestStatusAndExceptionInformationWhenThrowingBusinessException() throws Exception {
        HttpStatus expectedHttpStatus = HttpStatus.BAD_REQUEST;
        String expectedDescription = "BusinessException";

        when(stubController.createSubmission(any(SubmissionDto.class))).thenThrow(new BusinessException(expectedDescription));

        assertThat(mockMvc.perform(post(REQUEST_URL).contentType(jsonMediaType).content(submissionJson))
                .andExpect(jsonPath(STATUS_CODE_FIELD, is(expectedHttpStatus.value())))
                .andExpect(jsonPath(STATUS_NAME_FIELD, is(expectedHttpStatus.name())))
                .andExpect(jsonPath(DESCRIPTION_FIELD, is(expectedDescription))));
    }

    /**
     * Tests case when any RuntimeException occur
     *
     * @throws Exception throws exception
     */
    @Test
    public void shouldReturnInternalServerErrorStatusAndExceptionInformationWhenThrowingAnyRuntimeException() throws Exception {
        HttpStatus expectedHttpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        String expectedDescription = "RuntimeException";

        when(stubController.createSubmission(any(SubmissionDto.class))).thenThrow(new RuntimeException(expectedDescription));

        assertThat(mockMvc.perform(post(REQUEST_URL).contentType(jsonMediaType).content(submissionJson))
                .andExpect(jsonPath(STATUS_CODE_FIELD, is(expectedHttpStatus.value())))
                .andExpect(jsonPath(STATUS_NAME_FIELD, is(expectedHttpStatus.name())))
                .andExpect(jsonPath(DESCRIPTION_FIELD, is(expectedDescription))));
    }

}

package com.epam.autograder.core.exception.controller;

import com.epam.autograder.core.CoreApplication;
import com.epam.autograder.core.exception.BusinessException;
import com.epam.autograder.core.resource.SubmissionResource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.Charset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Class tests exception handling in application
 *
 * @author Eduard Khachirov
 * @see ExceptionController
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CoreApplication.class)
public class ExceptionControllerTest {

    private static final String JSON_RESPONSE_STATUS_CODE_FIELD = "$.statusCode";
    private static final String JSON_RESPONSE_STATUS_NAME_FIELD = "$.statusName";
    private static final String JSON_RESPONSE_DESCRIPTION_FIELD = "$.description";
    private static final String REQUEST_URL = "/submission";
    private static final String SUBMISSION_JSON = "{\"submissionId\" : \"\","
            + " \"environmentId\" : \"gcdp_autograder_hello_world\", "
            + "\"inputSource\" : \"GIT\","
            + "  \"inputData\" : \"git@git.epam.com:.../...git\"}";

    private static final MediaType JSON_MEDIA_TYPE = new MediaType(MediaType.APPLICATION_JSON, Charset.forName("utf8"));

    @Autowired
    private ExceptionController exceptionController;

    @Mock
    private SubmissionResource stubController;

    private MockMvc mockMvc;

    /**
     * Sets up resources for testing
     */
    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(stubController)
                .setControllerAdvice(exceptionController)
                .build();

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

        when(stubController.createSubmission(any())).thenThrow(new BusinessException(expectedDescription));

        assertThat(mockMvc.perform(post(REQUEST_URL).contentType(JSON_MEDIA_TYPE).content(SUBMISSION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(JSON_RESPONSE_STATUS_CODE_FIELD, is(expectedHttpStatus.value())))
                .andExpect(jsonPath(JSON_RESPONSE_STATUS_NAME_FIELD, is(expectedHttpStatus.name())))
                .andExpect(jsonPath(JSON_RESPONSE_DESCRIPTION_FIELD, is(expectedDescription))));
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

        when(stubController.createSubmission(any())).thenThrow(new RuntimeException(expectedDescription));

        assertThat(mockMvc.perform(post(REQUEST_URL).contentType(JSON_MEDIA_TYPE).content(SUBMISSION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath(JSON_RESPONSE_STATUS_CODE_FIELD, is(expectedHttpStatus.value())))
                .andExpect(jsonPath(JSON_RESPONSE_STATUS_NAME_FIELD, is(expectedHttpStatus.name())))
                .andExpect(jsonPath(JSON_RESPONSE_DESCRIPTION_FIELD, is(expectedDescription))));
    }

}

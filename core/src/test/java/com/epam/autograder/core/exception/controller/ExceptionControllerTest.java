package com.epam.autograder.core.exception.controller;

import com.epam.autograder.core.CoreApplication;
import com.epam.autograder.core.exception.BusinessException;
import com.epam.autograder.core.exception.StubController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Autowired
    private ExceptionController exceptionController;

    @Mock
    private StubController stubController;

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
        String requestUrl = "/throwException";
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        String description = "BusinessException";

        when(stubController.handleRequest()).thenThrow(new BusinessException(description));

        assertThat(mockMvc.perform(get(requestUrl))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(JSON_RESPONSE_STATUS_CODE_FIELD, is(httpStatus.value())))
                .andExpect(jsonPath(JSON_RESPONSE_STATUS_NAME_FIELD, is(httpStatus.name())))
                .andExpect(jsonPath(JSON_RESPONSE_DESCRIPTION_FIELD, is(description))));
    }

    /**
     * Tests case when any RuntimeException occur
     *
     * @throws Exception throws exception
     */
    @Test
    public void shouldReturnInternalServerErrorStatusAndExceptionInformationWhenThrowingAnyRuntimeException() throws Exception {
        String requestUrl = "/throwException";
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        String description = "RuntimeException";

        when(stubController.handleRequest()).thenThrow(new RuntimeException(description));

        assertThat(mockMvc.perform(get(requestUrl))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath(JSON_RESPONSE_STATUS_CODE_FIELD, is(httpStatus.value())))
                .andExpect(jsonPath(JSON_RESPONSE_STATUS_NAME_FIELD, is(httpStatus.name())))
                .andExpect(jsonPath(JSON_RESPONSE_DESCRIPTION_FIELD, is(description))));
    }

}

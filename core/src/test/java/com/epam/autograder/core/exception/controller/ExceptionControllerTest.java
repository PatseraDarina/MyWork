package com.epam.autograder.core.exception.controller;

import com.epam.autograder.core.CoreApplication;
import com.epam.autograder.core.exception.StubController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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

    @Autowired
    private StubController stubController;

    private MockMvc mockMvc;

    /**
     * Sets up resources for testing
     */
    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(stubController)
                .setControllerAdvice(new ExceptionController())
                .build();
    }

    /**
     * Tests case when BusinessException occurs
     *
     * @throws Exception throws exception
     */
    @Test
    public void shouldReturnBadRequestStatusAndExceptionInformationWhenThrowingBusinessException() throws Exception {
        String requestUrl = "/businessError";
        String expectedJson = "{\n"
                + "    \"statusCode\": 400,\n"
                + "    \"statusName\": \"BAD_REQUEST\",\n"
                + "    \"description\": \"BusinessException\"\n"
                + "}";

        assertThat(mockMvc.perform(get(requestUrl))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(expectedJson)));
    }

    /**
     * Tests case when any RuntimeException occur
     *
     * @throws Exception throws exception
     */
    @Test
    public void shouldReturnInternalServerErrorStatusAndExceptionInformationWhenThrowingAnyRuntimeException() throws Exception {
        String requestUrl = "/runtimeError";
        String expectedJson = "{\n"
                + "    \"statusCode\": 500,\n"
                + "    \"statusName\": \"INTERNAL_SERVER_ERROR\",\n"
                + "    \"description\": \"RuntimeException\"\n"
                + "}";

        assertThat(mockMvc.perform(get(requestUrl))
                .andExpect(status().isInternalServerError())
                .andExpect(content().json(expectedJson)));
    }

}

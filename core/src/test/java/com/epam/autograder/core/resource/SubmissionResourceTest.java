package com.epam.autograder.core.resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

/**
 * Test class for testing SubmissionResource functionality
 */
public class SubmissionResourceTest extends MockMvcBaseIntegrationTest {

    private static final String REQUEST_BODY = "{\"submissionId\" : \"\", \"environmentId\" : \"gcdp_autograder_hello_world\", "
            + "\"inputSource\" : \"GIT\",  \"inputData\" : \"git@git.epam.com:.../...git\"}";
    private static final String URL_TEMPLATE = "/submission";
    private static final String WRONG_URL_TEMPLATE = "/submission111";
    @Autowired
    private MediaType applicationJsonUtf8 = new MediaType(
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    /**
     * Test successful status of POST
     *
     * @throws Exception if a problem occurs
     */
    @Test
    public void shouldReturnStatusSuccess() throws Exception {
        assertThat(mockMvc.perform(RestDocumentationRequestBuilders.post(URL_TEMPLATE).content(REQUEST_BODY)
                .contentType(applicationJsonUtf8))
                .andExpect(status().isOk()));
    }

    /**
     * Test client error of POST
     *
     * @throws Exception if a problem occurs
     */
    @Test
    public void shouldReturnStatusClientErrorWhenURLTemplateIsWrong() throws Exception {
        assertThat(mockMvc.perform(RestDocumentationRequestBuilders.post(WRONG_URL_TEMPLATE).content(REQUEST_BODY)
                .contentType(applicationJsonUtf8))
                .andExpect(status().is4xxClientError()));
    }

    //TODO REQUEST_BODY to JSONPath
    //TODO Implement tests for cases: BadRequest, InternalServerError
}



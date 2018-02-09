package com.epam.autograder.core.resource;

import com.epam.autograder.core.entity.InputSource;
import com.epam.autograder.core.entity.Submission;
import com.epam.autograder.core.repository.SubmissionRepository;
import com.epam.autograder.core.service.SubmissionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import java.nio.charset.Charset;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for testing SubmissionResource functionality
 */

public class SubmissionResourceTest extends MockMvcBaseIntegrationTest {
    private static final String URL_TEMPLATE = "/submission";
    private MediaType applicationJsonUtf8 = new MediaType(
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
    private static final String ENVIRONMENT_ID = "environmentId";
    private static final String INPUT_SOURCE = "inputSource";
    private static final String INPUT_DATA = "inputData";
    private static final String ENVIRONMENT_ID_VALUE = "gcdp_autograder_hello_world";
    private static final String INPUT_SOURCE_VALUE = "GIT";
    private static final String INPUT_DATA_VALUE = "git@git.epam.com:.../...git";
    private Submission submission;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private SubmissionRepository submissionRepository;
    @Autowired
    private SubmissionService submissionService;

    @Before
    public void setUp() {
        submission = new Submission();
        submission.setInputSource(InputSource.GIT);
        submission.setInputData(INPUT_DATA_VALUE);
        submission.setEnvironmentId(ENVIRONMENT_ID_VALUE);
    }

    /**
     * Test successful status of POST
     *
     * @throws Exception if a problem occurs
     */
    @Test
    public void shouldReturnStatusSuccess() throws Exception {
        when(submissionService.createSubmission(any(Submission.class))).thenReturn(submissionRepository.save(submission));
        mockMvc.perform(RestDocumentationRequestBuilders.post(URL_TEMPLATE).content(objectMapper.writeValueAsString(submission))
                .contentType(applicationJsonUtf8))
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
    public void shouldReturnClientStatusErrorWhenBadRequest() throws Exception {
        String INCORRECT_REQUEST_BODY = "{\"submissionId\" : , \"environmentId\" : \"gcdp_autograder_hello_world\", "
                + "\"inputSource\" : \"GIT\",  \"inputData\" : \"git@git.epam.com:.../...git\"}";
        mockMvc.perform(RestDocumentationRequestBuilders.post(URL_TEMPLATE).content(objectMapper.writeValueAsString(INCORRECT_REQUEST_BODY))
                .contentType(applicationJsonUtf8))
                .andExpect(status().is4xxClientError());
    }

    /**
     * Test server error of POST
     *
     * @throws Exception if a problem occurs
     */
    @Test
    public void shouldReturnServerStatusErrorWhenErrorOnServer() throws Exception {
        when(submissionService.createSubmission(any(Submission.class))).thenThrow(RuntimeException.class);
        mockMvc.perform(RestDocumentationRequestBuilders.post(URL_TEMPLATE).content(objectMapper.writeValueAsString(submission))
                .contentType(applicationJsonUtf8))
                .andExpect(status().is5xxServerError());
    }
}

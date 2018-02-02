package com.epam.autograder.core.resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.epam.autograder.core.MockMvcBase;

/**
 * Test class for testing SubmissionResource functionality
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class SubmissionResourceTest extends MockMvcBase {

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private SubmissionResource submissionResource;
    private String requestBody = "{\"submissionId\" : \"\", \"environmentId\" : \"gcdp_autograder_hello_world\", "
            + "\"inputSource\" : \"GIT\",  \"inputData\" : \"git@git.epam.com:.../...git\"}";
    private MediaType applicationJsonUtf8 = new MediaType(
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    /**
     * Before tests
     */
    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /**
     * Assest that submissionResource is not null
     */
    @Test
    public void contextLoads() {
        assertThat(submissionResource).isNotNull();
    }

    /**
     *
     * @throws Exception
     */
    @Test
    void testPostIsOk() throws Exception {
        assertThat(mockMvc.perform(RestDocumentationRequestBuilders.post("/submission").content(requestBody)
                .contentType(applicationJsonUtf8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.submissionId", is(1)))
                .andExpect(jsonPath("$.environmentId", is("gcdp_autograder_hello_world")))
                .andExpect(jsonPath("$.inputSource", is("GIT")))
                .andExpect(jsonPath("$.inputData", is("git@git.epam.com:.../...git"))));
    }

    /**
     *
     * @throws Exception
     */
    @Test
    void testPost400() throws Exception {
        assertThat(mockMvc.perform(RestDocumentationRequestBuilders.post("/submission121").content(requestBody)
                .contentType(applicationJsonUtf8))
                .andExpect(status().is4xxClientError()));
    }
}


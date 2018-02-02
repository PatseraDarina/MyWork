package com.epam.autograder.core.resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import com.epam.autograder.core.MockMvcBase;

/**
 * Test class for testing SubmissionResource functionality
 */
@SpringBootTest
public class SubmissionResourceTest extends MockMvcBase {

    @Autowired
    private SubmissionResource submissionResource;

    private String requestBody = "{\"submissionId\" : \"\", \"environmentId\" : \"gcdp_autograder_hello_world\", "
            + "\"inputSource\" : \"GIT\",  \"inputData\" : \"git@git.epam.com:.../...git\"}";

    private MediaType applicationJsonUtf8 = new MediaType(
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    /**
     * ghghghg
     */
    @Test
    public void contextLoads() {
        assertThat(submissionResource).isNotNull();
    }

    /**
     * gfghfjghfj,df
     * @throws Exception fdffjdkjf
     */

    @Test
    public void testPostIsOk() throws Exception {
        assertThat(mockMvc.perform(RestDocumentationRequestBuilders.post("/submission").content(requestBody)
                .contentType(applicationJsonUtf8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.submissionId", is(2)))
                .andExpect(jsonPath("$.environmentId", is("gcdp_autograder_hello_world")))
                .andExpect(jsonPath("$.inputSource", is("GIT")))
                .andExpect(jsonPath("$.inputData", is("git@git.epam.com:.../...git"))));
    }

    /**
     * hgfghgghfgh
     *
     * @throws Exception ghhkkfj
     */
    @Test
    public void testPost400() throws Exception {
        assertThat(mockMvc.perform(RestDocumentationRequestBuilders.post("/submission121").content(requestBody)
                .contentType(applicationJsonUtf8))
                .andExpect(status().is4xxClientError()));
    }
}


package com.epam.autograder.runner.resource;

import com.epam.autograder.runner.setup.MockMvcBase;
import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Class consist of methods that tests {@DockerResource}
 */
public class DockerResourceTest extends MockMvcBase {

    /**
     * Tests creating container
     *
     * @throws Exception exception
     */
    @Test
    public void createContainer() throws Exception {
        mockMvc.perform(post("/containers"))
                .andExpect(status().is4xxClientError());
    }

    /**
     * Tests context loading
     */
    @Test
    public void contextLoads() {
        org.junit.Assert.assertNotNull(context);
    }
}

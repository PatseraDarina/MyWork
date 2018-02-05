package com.epam.autograder.runner.controller;

import com.epam.autograder.runner.setup.MockMvcBase;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 */
public class TopicControllerTest extends MockMvcBase {

    /**
     * @throws Exception exception
     */
    @Test
    public void getTopic() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.get("/topics/{id}", 1))
                .andExpect(status().isOk());
    }

    /**
     * @throws Exception exception
     */
    @Test
    public void addTopic() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.post("/topics")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"2\",\"name\":\"JavaScript\"}"))
                .andExpect(status().isOk());
    }

    /**
     * @throws Exception exception
     */
    @Test
    public void getAllTopics() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.get("/topics"))
                .andExpect(status().isOk());
    }

}

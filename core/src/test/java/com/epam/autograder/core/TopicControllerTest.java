package com.epam.autograder.core;

import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TopicControllerTest extends MockMvcBase {

    @Test
    public void getTopic() throws Exception {
        mockMvc.perform(get("/topics/{id}",0))
                .andExpect(status().isOk());
    }
}

package com.epam.autograder.core;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * topicController test
 */
public class TopicControllerTest extends BaseIntegrationTest {

    /**
     * test to check the topic by id
     *
     * @throws Exception check on exception
     */
    @Test
    public void getTopic() throws Exception {
        /**
         * get url with parameter
         */
        ResultActions resultActions = mockMvc.perform(get("/topics/{id}", 0))
                .andExpect(status().isOk());
        Assert.assertNotNull(resultActions);
    }
}

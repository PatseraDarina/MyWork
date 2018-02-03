package com.epam.autograder.runner.controller;

import com.epam.autograder.runner.entity.Submission;
import com.epam.autograder.runner.result.Result;
import com.epam.autograder.runner.service.DockerService;
import com.epam.autograder.runner.setup.MockMvcBase;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Class consist of methods that tests {@code DockerResource}
 */
public class DockerControllerTest extends MockMvcBase {

    @InjectMocks
    private DockerController controller;
    @Mock
    private DockerService service;
    @Mock
    private Submission submission;

    /**
     * Tests creating container
     *
     * @throws Exception exception
     */
    @Test
    public void createContainer() throws Exception {
        ResponseEntity<Result> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        mockMvc.perform(post("/containers"))
                .andExpect(status().is4xxClientError());
        assertThat(controller.createContainer(submission), is(responseEntity));
    }

    /**
     * Tests context loading
     */
    @Test
    public void contextLoads() {
        org.junit.Assert.assertNotNull(context);
    }
}

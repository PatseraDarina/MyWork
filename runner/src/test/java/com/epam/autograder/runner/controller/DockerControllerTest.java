package com.epam.autograder.runner.controller;

import com.epam.autograder.runner.entity.Sandbox;
import com.epam.autograder.runner.result.Result;
import com.epam.autograder.runner.service.DockerService;
import com.epam.autograder.runner.setup.MockMvcBase;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Class consist of methods that tests {@code DockerResource}
 */
public class DockerControllerTest extends MockMvcBase {

    @InjectMocks
    private SandboxController controller;
    @Mock
    private DockerService service;
    @Mock
    private Sandbox sandbox;
    @Mock
    private BindingResult bindingResult;
    @Mock
    private Map<Result, HttpStatus> statusMap;


    /**
     * Tests creating container
     *
     * @throws Exception exception
     */
    @Test
    public void createContainer() throws Exception {
        when(service.runDocker(sandbox)).thenReturn(Result.OK);
        when(statusMap.get(Result.OK)).thenReturn(HttpStatus.OK);
        ResponseEntity<?> responseEntity = new ResponseEntity(HttpStatus.OK);
        mockMvc.perform(post("/sandboxes").contentType(MediaType.APPLICATION_JSON).content(" {\n"
                + " \"id\" : \"1\",\n"
                + " \"type\" : \"busybox\",\n"
                + " \"payload\" : \"payload\",\n"
                + " \"status\" : \"COMPLETE\"\n"
                + " }"))
                .andExpect(status().is5xxServerError());
        assertThat(controller.acceptSandbox(sandbox, bindingResult), is(responseEntity));
    }

    /**
     * Tests creating container with not valid parameters
     *
     * @throws Exception exception
     */
    @Test
    public void createContainerWithNotValidParameters() throws Exception {
        when(service.runDocker(sandbox)).thenReturn(Result.BAD_REQUEST);
        when(statusMap.get(Result.BAD_REQUEST)).thenReturn(HttpStatus.BAD_REQUEST);
        when(bindingResult.hasErrors()).thenReturn(true);
        ResponseEntity<?> responseEntityWithMastake = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        mockMvc.perform(post("/sandboxes"))
                .andExpect(status().isBadRequest());
        assertThat(controller.acceptSandbox(sandbox, bindingResult), is(responseEntityWithMastake));
    }

    /**
     * Tests context loading
     */
    @Test
    public void contextLoads() {
        org.junit.Assert.assertNotNull(context);
    }
}

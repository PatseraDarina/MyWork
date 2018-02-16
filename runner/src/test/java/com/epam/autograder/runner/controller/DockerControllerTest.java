package com.epam.autograder.runner.controller;

import com.epam.autograder.runner.entity.Sandbox;
import com.epam.autograder.runner.entity.SandboxStatus;
import com.epam.autograder.runner.result.Result;
import com.epam.autograder.runner.service.DockerService;
import com.epam.autograder.runner.setup.MockMvcBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.validation.BindingResult;

import java.nio.charset.Charset;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Class consist of methods that tests {@code DockerResource}
 */
class DockerControllerTest extends MockMvcBase {

    private static final String URL = "/sandboxes";
    @InjectMocks
    private SandboxController controller;
    @Mock
    private DockerService service;
//    @Mock
//    private Sandbox sandbox;
    @Mock
    private BindingResult bindingResult;
    @Mock
    private Map<Result, HttpStatus> statusMap;

    private Sandbox sandbox;

    private static final String REQUEST_BODY = " {\n"
            + " \"id\":\"1\",\n"
            + " \"type\":\"hello-world\",\n"
            + " \"payload\":\"payload\",\n"
            + " \"status\":\"COMPLETE\"\n"
            + " }";

//    private MediaType applicationJsonUtf8 = new MediaType(
//            MediaType.APPLICATION_JSON.getType(),
//            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));


    @BeforeEach
    void initSandbox() {
        sandbox = new Sandbox();
        sandbox.setId("1234");
        sandbox.setType("busybox");
        sandbox.setPayload("git.epam.com");
        sandbox.setStatus(SandboxStatus.COMPLETE);
    }


    /**
     * Tests creating container
     *
     * @throws Exception exception
     */
    @Test
    @DisplayName("Check that method acceptSandbox should return status success")
    void shouldReturnStatusSuccess() throws Exception {
//        when(service.runDocker(sandbox)).thenReturn(Result.OK);
//        when(statusMap.get(Result.OK)).thenReturn(HttpStatus.OK);
        ResponseEntity<?> responseEntity = new ResponseEntity(HttpStatus.OK);
        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(sandbox)))
                .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("id", is(sandbox.getId())))
        .andExpect(jsonPath("type", is(sandbox.getType())))
        .andExpect(jsonPath("payload", is(sandbox.getPayload())))
        .andExpect(jsonPath("status", is(sandbox.getStatus())));

        //assertThat(controller.acceptSandbox(sandbox, bindingResult), is(responseEntity));
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
        ResponseEntity<?> responseEntityWithMistake = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(REQUEST_BODY)))
                .andExpect(status().is4xxClientError());
        assertThat(controller.acceptSandbox(sandbox, bindingResult), is(responseEntityWithMistake));
    }

    /**
     * Tests context loading
     */
    @Test
    public void contextLoads() {
        org.junit.Assert.assertNotNull(context);
    }
}

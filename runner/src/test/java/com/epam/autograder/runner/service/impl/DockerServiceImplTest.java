package com.epam.autograder.runner.service.impl;

import com.epam.autograder.runner.entity.Submission;
import com.epam.autograder.runner.result.Result;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.*;
import com.github.dockerjava.api.model.Info;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Tests docker service.
 */
@RunWith(MockitoJUnitRunner.class)
public class DockerServiceImplTest {

    private static final String IMAGE = "image";

    @Mock
    private DockerClient dockerClient;
    @InjectMocks
    private DockerServiceImpl dockerService;
    @Mock
    private CreateContainerResponse containerResponse;
    @Mock
    private WaitContainerCmd waitContainerCmd;
    @Mock
    private StartContainerCmd startContainerCmd;
    @Mock
    private StopContainerCmd stopContainerCmd;
    @Mock
    private CreateContainerCmd createContainerCmd;
    @Mock
    private Info info;
    @Mock
    private InfoCmd infoCmd;
    @Mock
    private Submission submission;

    /**
     * Tests of docker running.
     */
    @Test
    public void runDocker() {
        when(dockerClient.infoCmd()).thenReturn(infoCmd);
        when(infoCmd.exec()).thenReturn(info);
        when(submission.getSubmissionId()).thenReturn(123L);
        when(submission.getEnvironmentId()).thenReturn(anyString());
        when(dockerClient.createContainerCmd(IMAGE)).thenReturn(createContainerCmd);
        when(dockerClient.createContainerCmd(IMAGE).withCmd("sleep", "9999")).thenReturn(createContainerCmd);
        when(dockerClient.createContainerCmd(IMAGE).withCmd("sleep", "9999").withName(String.valueOf(submission.getSubmissionId())))
            .thenReturn(createContainerCmd);
        when(createContainerCmd.exec()).thenReturn(containerResponse);
        mockDockerClient();

        assertThat(dockerService.runDocker(submission), is(Result.OK));
    }

    private void mockDockerClient() {
        when(dockerClient.startContainerCmd(containerResponse.getId())).thenReturn(startContainerCmd);
        when(dockerClient.waitContainerCmd(containerResponse.getId())).thenReturn(waitContainerCmd);
        when(dockerClient.stopContainerCmd(containerResponse.getId())).thenReturn(stopContainerCmd);
    }


}

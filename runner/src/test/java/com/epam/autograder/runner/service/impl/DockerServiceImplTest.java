package com.epam.autograder.runner.service.impl;

import com.epam.autograder.runner.entity.Submission;
import com.epam.autograder.runner.result.Result;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.StopContainerCmd;
import com.github.dockerjava.api.command.StartContainerCmd;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.command.WaitContainerCmd;
import com.github.dockerjava.api.command.InfoCmd;
import com.github.dockerjava.api.model.Info;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

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
        given(dockerClient.infoCmd()).willReturn(infoCmd);
        given(infoCmd.exec()).willReturn(info);
        given(submission.getSubmissionId()).willReturn(123L);
        given(submission.getEnvironmentId()).willReturn(anyString());
        given(dockerClient.createContainerCmd(IMAGE)).willReturn(createContainerCmd);
        given(dockerClient.createContainerCmd(IMAGE).withCmd("sleep", "9999")).willReturn(createContainerCmd);
        given(dockerClient.createContainerCmd(IMAGE).withCmd("sleep", "9999").withName(String.valueOf(submission.getSubmissionId())))
                .willReturn(createContainerCmd);
        given(createContainerCmd.exec()).willReturn(containerResponse);
        mockDockerClient();

        assertThat(dockerService.runDocker(submission), is(Result.OK));
    }

    private void mockDockerClient() {
        given(dockerClient.startContainerCmd(containerResponse.getId())).willReturn(startContainerCmd);
        given(dockerClient.waitContainerCmd(containerResponse.getId())).willReturn(waitContainerCmd);
        given(dockerClient.stopContainerCmd(containerResponse.getId())).willReturn(stopContainerCmd);
    }


}

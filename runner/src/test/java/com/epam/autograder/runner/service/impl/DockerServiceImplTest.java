package com.epam.autograder.runner.service.impl;

import com.epam.autograder.runner.entity.Sandbox;
import com.epam.autograder.runner.result.Result;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.InfoCmd;
import com.github.dockerjava.api.command.StartContainerCmd;
import com.github.dockerjava.api.model.Info;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
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
    private StartContainerCmd startContainerCmd;
    @Mock
    private CreateContainerCmd createContainerCmd;
    @Mock
    private Info info;
    @Mock
    private InfoCmd infoCmd;
    @Mock
    private Sandbox sandbox;

    /**
     * Tests of docker running.
     */
    @Test
    public void shouldReturnResultOkAfterRunDocker() {
        when(dockerClient.infoCmd()).thenReturn(infoCmd);
        when(infoCmd.exec()).thenReturn(info);
        when(dockerClient.createContainerCmd(sandbox.getType())).thenReturn(createContainerCmd);
        when(dockerClient.createContainerCmd(sandbox.getType()).withName(String.valueOf(sandbox.getId())))
                .thenReturn(createContainerCmd);
        when(createContainerCmd.exec()).thenReturn(containerResponse);
        when(dockerClient.startContainerCmd(containerResponse.getId())).thenReturn(startContainerCmd);
        assertThat(dockerService.runDocker(sandbox), is(Result.OK));
    }

    /**
     * Tests of status getting.
     */
    /*@Test
    public void getStatus() {
      when(dockerService.getStatus("id")).thenReturn(SandboxStatus.COMPLETE);
        assertThat(dockerService.getStatus("id"), is(SandboxStatus.COMPLETE));
    }*/
}

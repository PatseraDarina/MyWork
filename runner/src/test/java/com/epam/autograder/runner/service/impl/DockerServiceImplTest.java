package com.epam.autograder.runner.service.impl;

import com.epam.autograder.runner.entity.Submission;
import com.epam.autograder.runner.result.Result;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.InfoCmd;
import com.github.dockerjava.api.command.StartContainerCmd;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.Info;
import com.github.dockerjava.api.model.Volume;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * Tests docker service.
 */
@RunWith(MockitoJUnitRunner.class)
public class DockerServiceImplTest {
    private static final String INPUT_PAYLOAD_PATH = "D:" + File.separator + "AutoGrader_Winter" + File.separator
            + "EPM-RDK1-AutoGrader"
            + File.separator + "var" + File.separator + "runner" + File.separator + "input" + File.separator
            + "payload" + File.separator + "payload";
    private static final String OUTPUT_PATH = "D:" + File.separator + "AutoGrader_Winter" + File.separator
            + "EPM-RDK1-AutoGrader" + File.separator + "var" + File.separator + "runner" + File.separator + "output";
    private static final String FILE_DIRECTORY = "var" + File.separator;
    private static final String FILE_NAME = "payload";
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
    private Submission submission;
    @Autowired
    @Qualifier("inVolume")
    private Volume volume1;
    @Autowired
    @Qualifier("outVolume")
    private Volume volume2;
    @Autowired
    @Qualifier("inBind")
    private Bind bind1;
    @Autowired
    @Qualifier("outBind")
    private Bind bind2;

    /**
     * Tests of docker running.
     */
    @Test
    public void runDocker() {
        when(dockerClient.infoCmd()).thenReturn(infoCmd);
        when(infoCmd.exec()).thenReturn(info);
        when(submission.getSubmissionId()).thenReturn(123L);
        when(submission.getEnvironmentId()).thenReturn(IMAGE);
        when(dockerClient.createContainerCmd(IMAGE)).thenReturn(createContainerCmd);
        when(dockerClient.createContainerCmd(IMAGE).withVolumes(volume1, volume2)).thenReturn(createContainerCmd);
        when(dockerClient.createContainerCmd(IMAGE).withVolumes(volume1, volume2).withBinds(bind1, bind2))
                .thenReturn(createContainerCmd);
        when(dockerClient.createContainerCmd(IMAGE).withVolumes(volume1, volume2).withBinds(bind1, bind2)
                .withName(String.valueOf(submission.getSubmissionId()))).thenReturn(createContainerCmd);

        when(createContainerCmd.exec()).thenReturn(containerResponse);
        when(dockerClient.startContainerCmd(containerResponse.getId())).thenReturn(startContainerCmd);

        assertThat(dockerService.runDocker(submission), is(Result.OK));

    }

    /**
     * Destroys test folder
     *
     * @throws IOException file directory exception
     */
    @After
    public void destroyTestFolder() throws IOException {
        File file = new File(FILE_DIRECTORY);
        FileUtils.deleteDirectory(file);
    }
}

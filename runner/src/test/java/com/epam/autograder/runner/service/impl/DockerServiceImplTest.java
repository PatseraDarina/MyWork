package com.epam.autograder.runner.service.impl;

import com.epam.autograder.runner.entity.Sandbox;
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
import org.junit.Before;
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

    private static final String FILE_DIRECTORY = File.separator + "var" + File.separator + "runner"
            + File.separator + "123" + File.separator + "input" + File.separator
            + "payload";
    private static final String OUTPUT_PATH = File.separator + "var" + File.separator + "runner"
            + File.separator + "123" + File.separator + "output" + File.separator;

    //    private static final String INPUT_PAYLOAD_PATH = "D:" + File.separator + "AutoGrader_Winter" + File.separator
//            + "EPM-RDK1-AutoGrader"
//            + File.separator + "var";
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
    @Mock
    private File file;
    @Autowired
    @Qualifier("inVolume")
    private Volume volume1;
    @Autowired
    @Qualifier("outVolume")
    private Volume volume2;
    private Bind bind1;
    private Bind bind2;
    private String current;

    /**
     * Create directory
     *
     * @throws IOException file directory exception
     */
    @Before
    public void setUp() throws IOException {
        current = new java.io.File(".").getCanonicalPath();
        bind1 = new Bind(current + FILE_DIRECTORY, volume1);
        bind2 = new Bind(current + OUTPUT_PATH, volume2);
    }

    /**
     * Tests of docker running.
     */
    @Test
    public void shouldReturnResultOkAfterRunDocker() {
        when(dockerClient.infoCmd()).thenReturn(infoCmd);
        when(infoCmd.exec()).thenReturn(info);
        when(sandbox.getId()).thenReturn(("123"));
        when(dockerClient.createContainerCmd(sandbox.getType())).thenReturn(createContainerCmd);
        when(dockerClient.createContainerCmd(sandbox.getType()).withName(String.valueOf(sandbox.getId()))).thenReturn(createContainerCmd);
        when(dockerClient.createContainerCmd(IMAGE)).thenReturn(createContainerCmd);
        when(dockerClient.createContainerCmd(IMAGE).withVolumes(volume1, volume2)).thenReturn(createContainerCmd);
        when(dockerClient.createContainerCmd(IMAGE).withVolumes(volume1, volume2).withBinds(bind1, bind2))
                .thenReturn(createContainerCmd);
        when(dockerClient.createContainerCmd(IMAGE).withVolumes(volume1, volume2).withBinds(bind1, bind2).withName(String.valueOf(sandbox.getId())))
                .thenReturn(createContainerCmd);

        when(createContainerCmd.exec()).thenReturn(containerResponse);
        when(dockerClient.startContainerCmd(containerResponse.getId())).thenReturn(startContainerCmd);
        assertThat(dockerService.runDocker(sandbox), is(Result.BAD_REQUEST));
    }

    /**
     * Destroys test folder
     *
     * @throws IOException file directory exception
     */
    @After
    public void destroyTestFolder() throws IOException {
        File directory = new File(current + File.separator + "var" + File.separator);
        FileUtils.deleteDirectory(directory);
    }
}

package com.epam.autograder.runner.service.impl;

import com.epam.autograder.runner.entity.Submission;
import com.epam.autograder.runner.result.Result;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.InfoCmd;
import com.github.dockerjava.api.model.Info;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.core.command.InfoCmdImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests docker service.
 */
@RunWith(MockitoJUnitRunner.class)
public class DockerServiceImplTest {

    @InjectMocks
    private DockerServiceImpl dockerService;
    @Mock
    private Submission submission;
    @Mock
    private AnnotationConfigApplicationContext context;
    @Mock
    private Info info;
    private InfoCmd infoCmd = mock(InfoCmdImpl.class);

    private DockerClient dockerClient = mock(DockerClientImpl.class);


    /**
     * Tests of docker running.
     *
     */
    @Test
    public void runDocker() {
        dockerService = new DockerServiceImpl();
        when(dockerClient.infoCmd()).thenReturn(infoCmd);
        when(dockerClient.infoCmd().exec()).thenReturn(info);
        when(dockerService.runDocker(submission)).thenReturn(Result.OK);
        assertEquals(Result.OK, dockerService.runDocker(submission));
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
//        context.register(RunnerConfig.class);
//        context.refresh();
//
//        DockerService dockerService = (DockerService) context.getBean("dockerService");
//        Submission submission = new Submission();
//        submission.setEnvironmentId("busybox");
//       // assertThrows(ProcessingException.class, () -> dockerService.runDocker(submission));
//        dockerService.runDocker(submission);
    }

    /**
     *
     */
    @Test
    public void contextLoads() {
        org.junit.Assert.assertNotNull(context);
    }
}

package com.epam.autograder.runner;

import com.epam.autograder.runner.config.RunnerConfig;
import com.epam.autograder.runner.entity.Submission;
import com.epam.autograder.runner.service.DockerService;
import com.epam.autograder.runner.service.DockerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 */
@SpringBootApplication
public class RunnerApplication {
//    @Autowired
//    @Qualifier("dockerServiceImpl")
//    DockerService dockerService;
    /**
     * @param args Input args
     */
    public static void main(String[] args) {
        SpringApplication.run(RunnerApplication.class, args);
//
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(RunnerConfig.class);
        context.refresh();

        DockerService dockerService = (DockerService) context.getBean("dockerService");
//        DockerService dockerService = new DockerServiceImpl();
        dockerService.runDocker(new Submission());

    }
}

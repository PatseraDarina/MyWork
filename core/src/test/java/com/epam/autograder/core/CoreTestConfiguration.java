package com.epam.autograder.core;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import com.epam.autograder.core.service.SubmissionService;
import com.epam.autograder.core.service.impl.SubmissionServiceImpl;

import jetbrains.exodus.entitystore.PersistentEntityStore;
import jetbrains.exodus.entitystore.PersistentEntityStores;

@Configuration
public class CoreTestConfiguration {

    @Bean
    @Primary
    public SubmissionService submissionService() {
        return Mockito.mock(SubmissionServiceImpl.class);
    }

    @Bean
    @Primary
    @Profile("dev")
    public PersistentEntityStore persistentEntityStore() {
        String userDir = System.getProperty("user.dir");
        Path dirPath = Paths.get(userDir, "test");
        return PersistentEntityStores.newInstance(dirPath.toString());
    }
}

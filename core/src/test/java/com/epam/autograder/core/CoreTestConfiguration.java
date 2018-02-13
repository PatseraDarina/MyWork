package com.epam.autograder.core;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.annotation.PreDestroy;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.epam.autograder.core.service.SubmissionService;
import com.epam.autograder.core.service.impl.SubmissionServiceImpl;

import jetbrains.exodus.entitystore.PersistentEntityStore;
import jetbrains.exodus.entitystore.PersistentEntityStores;

@Configuration
public class CoreTestConfiguration {

    private PersistentEntityStore persistentEntityStore;

    @Bean
    @Primary
    public SubmissionService submissionService() {
        System.out.println("<<<<<<<<<<<<<< Mock service >>>>>>>>>>>>>>");
        return Mockito.mock(SubmissionServiceImpl.class);
    }

    @Bean
    @Primary
    public PersistentEntityStore persistentEntityStore() {
        String userDir = System.getProperty("user.dir");
        Path dirPath = Paths.get(userDir, "test");
        System.out.println("<<<<<<<<<< Mock Store >>>>>>>>>>");
        persistentEntityStore = PersistentEntityStores.newInstance(dirPath.toString());
        return persistentEntityStore;

        //return Mockito.mock(PersistentEntityStoreImpl.class);
    }

    @PreDestroy
    public void close() throws IOException {
        persistentEntityStore.clear();
        persistentEntityStore.close();
        System.out.println("<<<<<<<<<<<<<<<< Close store >>>>>>>>>>>>>>");
        FileUtils.deleteDirectory(new File(persistentEntityStore.getLocation()));
    }
}

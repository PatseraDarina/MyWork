package com.epam.autograder.core;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;

import jetbrains.exodus.entitystore.PersistentEntityStore;
import jetbrains.exodus.entitystore.PersistentEntityStores;

@Configuration
public class StoreTestConfiguration {

    private static final String USER_DIR = "user.dir";
    private static final String TEST_STORE_DIR = "testApplicationStore";

    @EventListener
    public void handleContextRefresh(ContextClosedEvent event) throws IOException {
        PersistentEntityStore store = getStore();
        store.close();
        FileUtils.deleteDirectory(new File(store.getLocation()));
    }

    @Primary
    @Bean
    public PersistentEntityStore getStore() {
        String userDir = System.getProperty(USER_DIR);
        Path dirPath = Paths.get(userDir, TEST_STORE_DIR);
        return PersistentEntityStores.newInstance(dirPath.toString());
    }
}

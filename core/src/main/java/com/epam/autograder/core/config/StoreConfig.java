package com.epam.autograder.core.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jetbrains.exodus.entitystore.PersistentEntityStore;
import jetbrains.exodus.entitystore.PersistentEntityStores;

/**
 * Configuration class for store
 */
@Configuration
public class StoreConfig {

    @Value("${store_dir}")
    private String dataStoreDir;

    /**
     * @return instance of store
     */
    @Bean
    public PersistentEntityStore getStore() {
        String userDir = System.getProperty("user.dir");
        Path dirPath = Paths.get(userDir, dataStoreDir);
        return PersistentEntityStores.newInstance(dirPath.toString());
    }
}

package com.epam.autograder.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jetbrains.exodus.entitystore.PersistentEntityStore;
import jetbrains.exodus.entitystore.PersistentEntityStores;

/**
 * Configuration class for Core module
 */
@Configuration
public class StoreConfiguration {

    /**
     * @return an instance of PersistentEntityStore
     */
    @Bean
    public PersistentEntityStore getSubmissionStore() {
        return PersistentEntityStores.newInstance(".SubmissionData");
    }
}

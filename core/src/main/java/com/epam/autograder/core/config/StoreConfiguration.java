package com.epam.autograder.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jetbrains.exodus.entitystore.PersistentEntityStore;
import jetbrains.exodus.entitystore.PersistentEntityStores;
import jetbrains.exodus.env.Environment;
import jetbrains.exodus.env.Environments;

/**
 * Configuration class for Core module
 */
@Configuration
public class StoreConfiguration {

    /**
     * @return new instance Environment
     */
    @Bean
    public Environment getEnvironment() {
        return Environments.newInstance("/src/store/.autoGraderData");
    }

    /**
     * @return new instance of PersistentEntityStore
     */
    @Bean
    public PersistentEntityStore getStore() {
        return PersistentEntityStores.newInstance(".SubmissionData");
    }
}

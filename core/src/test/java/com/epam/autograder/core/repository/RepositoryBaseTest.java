package com.epam.autograder.core.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.epam.autograder.core.CoreApplication;
import com.epam.autograder.core.StoreTestConfiguration;
import com.epam.autograder.core.dto.SubmissionDto;
import com.epam.autograder.core.mapper.Mapper;
import com.epam.autograder.core.repository.impl.SubmissionRepositoryImpl;

import jetbrains.exodus.entitystore.Entity;
import jetbrains.exodus.entitystore.PersistentEntityStore;

@ExtendWith({SpringExtension.class})
@TestPropertySource(locations = {"classpath:application.properties"})
@ContextConfiguration(classes = {CoreApplication.class, StoreTestConfiguration.class})
public class RepositoryBaseTest {

    @Autowired
    protected SubmissionRepositoryImpl submissionRepository;
    @Autowired
    @Qualifier("submissionToEntityMapper")
    private Mapper<SubmissionDto, Entity> mapper;
    @Autowired
    private PersistentEntityStore persistentEntityStore;

    /**
     * Close and clean test entity store
     */
    @AfterEach
    public void closeAndDeleteStore() {
        persistentEntityStore.clear();
    }
}

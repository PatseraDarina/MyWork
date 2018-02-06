package com.epam.autograder.core.repository.impl;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Repository;

import com.epam.autograder.core.entity.Submission;
import com.epam.autograder.core.repository.SubmissionRepository;

import jetbrains.exodus.entitystore.Entity;
import jetbrains.exodus.entitystore.EntityId;
import jetbrains.exodus.entitystore.PersistentEntityStore;
import jetbrains.exodus.entitystore.PersistentEntityStores;
import jetbrains.exodus.entitystore.StoreTransaction;

/**
 * Implementation for the Submission repository.
 *
 * @author Valeriia Chub
 */
@Repository
public class SubmissionRepositoryImpl implements SubmissionRepository {

    private static final String SUBMISSION_ENTITY_NAME = "Submission";
    private static final String ENVIRONMENT_ID_PROPERTY = "environmentId";
    private static final String INPUT_SOURCE_PROPERTY = "inputSource";
    private static final String INPUT_DATA_PROPERTY = "inputData";
    private static final String DATA_BASE_DIR = ".SubmissionData";
    private PersistentEntityStore store;


    /**
     * get instance of entity store
     */
    @PostConstruct
    public void initStore() {
        store = PersistentEntityStores.newInstance(DATA_BASE_DIR);
    }

    /**
     * close entity store
     */
    @PreDestroy
    public void closeStore() {
        store.close();
    }

    /**
     * @param submission a submission without id
     * @return submission with generated id
     */
    @Override
    public Submission save(Submission submission) {
        EntityId id = store.computeInTransaction(txn -> {
            Entity submissionEntity = submissionToEntity(submission, txn);
            txn.saveEntity(submissionEntity);
            return submissionEntity.getId();
        });
        submission.setSubmissionId(id.getLocalId());
        return submission;
    }

    /**
     * @param submission Submission
     * @param txn        StoreTransaction
     * @return new Entity "Submission"
     */
    private Entity submissionToEntity(Submission submission, StoreTransaction txn) {
        Entity submissionEntity = txn.newEntity(SUBMISSION_ENTITY_NAME);

        submissionEntity.setProperty(ENVIRONMENT_ID_PROPERTY, submission.getEnvironmentId());
        submissionEntity.setProperty(INPUT_SOURCE_PROPERTY, submission.getInputSource().toString());
        submissionEntity.setProperty(INPUT_DATA_PROPERTY, submission.getInputData());

        return submissionEntity;
    }
}

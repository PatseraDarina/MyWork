package com.epam.autograder.core.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.epam.autograder.core.entity.Submission;
import com.epam.autograder.core.repository.SubmissionRepository;

import jetbrains.exodus.entitystore.Entity;
import jetbrains.exodus.entitystore.EntityId;
import jetbrains.exodus.entitystore.PersistentEntityStore;
import jetbrains.exodus.entitystore.StoreTransaction;

/**
 * Implementation for the Submission repository.
 *
 * @author Valeriia Chub
 */
@Repository
public class SubmissionRepositoryImpl implements SubmissionRepository {

    private static final String SUBMISSION_ENTITY_NAME = "Submission";

    @Autowired
    private PersistentEntityStore store;

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

        submissionEntity.setProperty("environmentId", submission.getEnvironmentId());
        submissionEntity.setProperty("inputSource", submission.getInputSource().toString());
        submissionEntity.setProperty("inputData", submission.getInputData());

        return submissionEntity;
    }
}

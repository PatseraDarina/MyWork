package com.epam.autograder.core.repository.impl;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.epam.autograder.core.dto.SubmissionDto;
import com.epam.autograder.core.mapper.Mapper;
import com.epam.autograder.core.repository.SubmissionRepository;

import jetbrains.exodus.entitystore.Entity;
import jetbrains.exodus.entitystore.EntityId;
import jetbrains.exodus.entitystore.PersistentEntityStore;

/**
 * Implementation for the Submission repository.
 *
 * @author Valeriia Chub
 */
@Repository
public class SubmissionRepositoryImpl implements SubmissionRepository {

    private static final String SUBMISSION_ENTITY_NAME = "Submission";
    @Autowired
    @Qualifier("submissionToEntityMapper")
    private Mapper<SubmissionDto, Entity> mapper;
    private PersistentEntityStore store;

    /**
     * @param store store
     */
    @Autowired
    public void setStore(PersistentEntityStore store) {
        this.store = store;
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
    public SubmissionDto save(SubmissionDto submission) {
        EntityId id = store.computeInTransaction(txn -> {
            Entity submissionEntity = txn.newEntity(SUBMISSION_ENTITY_NAME);
            submissionEntity = mapper.map(submission, submissionEntity);
            txn.saveEntity(submissionEntity);
            return submissionEntity.getId();
        });
        submission.setSubmissionId(id.getLocalId());
        return submission;
    }
}

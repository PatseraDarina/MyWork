package com.epam.autograder.core.repository.impl;

import java.nio.file.Path;
import java.nio.file.Paths;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.epam.autograder.core.entity.Submission;
import com.epam.autograder.core.mapper.SubmissionMapper;
import com.epam.autograder.core.repository.SubmissionRepository;

import jetbrains.exodus.entitystore.Entity;
import jetbrains.exodus.entitystore.EntityId;
import jetbrains.exodus.entitystore.PersistentEntityStore;
import jetbrains.exodus.entitystore.PersistentEntityStores;

/**
 * Implementation for the Submission repository.
 *
 * @author Valeriia Chub
 */
@Repository
public class SubmissionRepositoryImpl implements SubmissionRepository {

    @Autowired
    private SubmissionMapper mapper;
    private PersistentEntityStore store;
    @Value("${store_dir}")
    private String dataStoreDir;

    /**
     * @param store store
     */
    public void setStore(PersistentEntityStore store) {
        this.store = store;
    }

    /**
     * get instance of entity store
     */
    @PostConstruct
    public void initStore() {
        String userDir = System.getProperty("user.dir");
        Path dirPath = Paths.get(userDir, dataStoreDir);

        store = PersistentEntityStores.newInstance(dirPath.toString());
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
            Entity submissionEntity = mapper.submissionToEntity(submission, txn);
            txn.saveEntity(submissionEntity);
            return submissionEntity.getId();
        });
        submission.setSubmissionId(id.getLocalId());
        return submission;
    }
}

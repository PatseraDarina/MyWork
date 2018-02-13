package com.epam.autograder.core.repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.epam.autograder.core.mapper.entity.SubmissionToEntityMapper;
import com.epam.autograder.core.repository.impl.SubmissionRepositoryImpl;

import jetbrains.exodus.entitystore.PersistentEntityStore;
import jetbrains.exodus.entitystore.PersistentEntityStores;

@SpringBootTest
@ExtendWith({SpringExtension.class})
@ActiveProfiles("default")
public class RepositoryBaseTest {

    @Autowired
    protected SubmissionRepositoryImpl submissionRepository;
    @Autowired
    protected SubmissionToEntityMapper mapper;
    @Value("${test_store_dir}")
    private String dataStoreDir;
    private PersistentEntityStore persistentEntityStore;

    @BeforeEach
    public void setUp() {
        String userDir = System.getProperty("user.dir");
        Path dirPath = Paths.get(userDir, dataStoreDir);

        persistentEntityStore = PersistentEntityStores.newInstance(dirPath.toString());
        submissionRepository.setStore(persistentEntityStore);
    }

    /**
     * Close and clean test entity store
     *
     * @throws IOException IOException
     */
    @AfterEach
    public void closeAndDeleteStore() throws IOException {
        persistentEntityStore.clear();
        persistentEntityStore.close();

        FileUtils.deleteDirectory(new File(persistentEntityStore.getLocation()));
    }
}

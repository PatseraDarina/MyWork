package com.epam.autograder.core.resource;

import static capital.scalable.restdocs.jackson.JacksonResultHandlers.prepareJackson;
import static capital.scalable.restdocs.response.ResponseModifyingPreprocessors.limitJsonArrayLength;
import static capital.scalable.restdocs.response.ResponseModifyingPreprocessors.replaceBinaryContent;
import static org.junit.Assert.assertNotNull;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.cli.CliDocumentation;
import org.springframework.restdocs.http.HttpDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.epam.autograder.core.repository.impl.SubmissionRepositoryImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import capital.scalable.restdocs.AutoDocumentation;
import jetbrains.exodus.entitystore.PersistentEntityStore;
import jetbrains.exodus.entitystore.PersistentEntityStores;

/**
 * mockMvcBase test
 */
@SpringBootTest
@ExtendWith({SpringExtension.class, RestDocumentationExtension.class})
public class MockMvcBaseIntegrationTest {

    private static final String CLASS_METHOD_NAME = "{class-name}/{method-name}";

    protected MockMvc mockMvc;
    @Autowired
    protected SubmissionRepositoryImpl submissionRepository;
    @Value("${test_store_dir}")
    private String dataStoreDir;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private WebApplicationContext context;
    private PersistentEntityStore persistentEntityStore;


    /**
     * Sets up.
     *
     * @param restDocumentation the rest documentation
     */
    @BeforeEach
    public void setUp(RestDocumentationExtension restDocumentation) {
        String http = "http";
        String localhost = "localhost";
        int port = 8080;
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .alwaysDo(prepareJackson(objectMapper))
                .alwaysDo(commonDocumentation())
                .apply(documentationConfiguration(restDocumentation)
                        .uris()
                        .withScheme(http)
                        .withHost(localhost)
                        .withPort(port)
                        .and().snippets()
                        .withDefaults(CliDocumentation.curlRequest(),
                                HttpDocumentation.httpRequest(),
                                HttpDocumentation.httpResponse(),
                                AutoDocumentation.requestFields(),
                                AutoDocumentation.responseFields(),
                                AutoDocumentation.pathParameters(),
                                AutoDocumentation.requestParameters(),
                                AutoDocumentation.description(),
                                AutoDocumentation.methodAndPath(),
                                AutoDocumentation.section()))
                .build();
    }

    /**
     * Initialize store
     */
    @Before
    public void initStore() {
        String userDir = System.getProperty("user.dir");
        Path dirPath = Paths.get(userDir, dataStoreDir);

        persistentEntityStore = PersistentEntityStores.newInstance(dirPath.toString());
        submissionRepository.setStore(persistentEntityStore);
    }

    /**
     * Close and clean entity store
     *
     * @throws IOException IOException
     */
    @After
    public void closeAndDeleteStore() throws IOException {
        persistentEntityStore.clear();
        persistentEntityStore.close();
        FileUtils.deleteDirectory(new File(persistentEntityStore.getLocation()));
    }

    protected RestDocumentationResultHandler commonDocumentation() {
        return document(CLASS_METHOD_NAME,
                preprocessRequest(), commonResponsePreprocessor());
    }

    protected OperationResponsePreprocessor commonResponsePreprocessor() {
        return preprocessResponse(replaceBinaryContent(), limitJsonArrayLength(objectMapper),
                prettyPrint());
    }

    /**
     * check on null
     */
    @Test
    public void contextLoads() {
        assertNotNull("Context shouldn't be null", context);
    }
}

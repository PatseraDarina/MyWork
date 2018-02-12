package com.epam.autograder.core.resource;

import capital.scalable.restdocs.AutoDocumentation;
import com.epam.autograder.core.CoreApplication;
import com.epam.autograder.core.CoreTestConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.cli.CliDocumentation;
import org.springframework.restdocs.http.HttpDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static capital.scalable.restdocs.jackson.JacksonResultHandlers.prepareJackson;
import static capital.scalable.restdocs.response.ResponseModifyingPreprocessors.limitJsonArrayLength;
import static capital.scalable.restdocs.response.ResponseModifyingPreprocessors.replaceBinaryContent;
import static org.junit.Assert.assertNotNull;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

/**
 * mockMvcBase test
 */
@WebAppConfiguration
@ContextConfiguration(classes = {CoreTestConfiguration.class, CoreApplication.class})
@ActiveProfiles("DEV")
@RunWith(SpringJUnit4ClassRunner.class)
public class MockMvcBaseIntegrationTest {

    private static final String CLASS_METHOD_NAME = "{class-name}/{method-name}";

    protected MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private WebApplicationContext context;

    /**
     * Sets up.
     *
     * @param restDocumentation the rest documentation
     */
//    @Before
//    public void setUp() throws Exception {
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

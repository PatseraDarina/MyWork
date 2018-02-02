package com.epam.autograder.core;

import capital.scalable.restdocs.AutoDocumentation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.cli.CliDocumentation;
import org.springframework.restdocs.http.HttpDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static capital.scalable.restdocs.jackson.JacksonResultHandlers.prepareJackson;
import static capital.scalable.restdocs.response.ResponseModifyingPreprocessors.limitJsonArrayLength;
import static capital.scalable.restdocs.response.ResponseModifyingPreprocessors.replaceBinaryContent;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

/**
 * mockMvcBase test
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public abstract class BaseIntegrationTest {

    private static final String HTTP = "http";
    private static final String LOCALHOST = "localhost";
    private static final int PORT = 8080;
    private static final String CLASS_METHOD_NAME = "{class-name}/{method-name}";
    @Rule
    public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();
    protected MockMvc mockMvc;
    @Autowired
    private ApplicationContext context;
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * setUp method
     *
     * @throws Exception check on exception
     */
    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders
                .webAppContextSetup((WebApplicationContext) context)
                .alwaysDo(prepareJackson(objectMapper))
                .alwaysDo(commonDocumentation())
                .apply(documentationConfiguration(restDocumentation)
                        .uris()
                        .withScheme(HTTP)
                        .withHost(LOCALHOST)
                        .withPort(PORT)
                        .and()
                        .snippets()
                        .withDefaults(
                                CliDocumentation.curlRequest(),
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

    private RestDocumentationResultHandler commonDocumentation() {
        return document(
                CLASS_METHOD_NAME,
                preprocessRequest(),
                commonResponsePreprocessor()
        );
    }

    private OperationResponsePreprocessor commonResponsePreprocessor() {
        return preprocessResponse(
                replaceBinaryContent(),
                limitJsonArrayLength(objectMapper),
                prettyPrint()
        );
    }
}
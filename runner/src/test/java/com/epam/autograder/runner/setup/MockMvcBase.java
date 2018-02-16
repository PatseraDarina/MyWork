package com.epam.autograder.runner.setup;

import capital.scalable.restdocs.AutoDocumentation;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.exceptions.base.MockitoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.cli.CliDocumentation;
import org.springframework.restdocs.http.HttpDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static capital.scalable.restdocs.jackson.JacksonResultHandlers.prepareJackson;
import static capital.scalable.restdocs.response.ResponseModifyingPreprocessors.limitJsonArrayLength;
import static capital.scalable.restdocs.response.ResponseModifyingPreprocessors.replaceBinaryContent;
import static org.junit.Assert.assertNotNull;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

/**
 * Initial tests class for generating documentation
 */
@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
 public class MockMvcBase {

    protected static final String CLASS_METHOD_NAME = "{class-name}/{method-name}";
    private static final String HTTP = "http";
    private static final String LOCALHOST = "localhost";
    private static final int PORT = 8080;

    @Autowired
    protected WebApplicationContext context;

    @Autowired
    protected ObjectMapper objectMapper;

    protected MockMvc mockMvc;

//    @Rule
//    public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    /**
     * Initial setup for tests. This method performed before each test method.
     *
     * @throws Exception exception
     */
    @BeforeEach
    void setUp(RestDocumentationExtension restDocumentation) throws Exception {
//        mockMvc = MockMvcBuilders
//                .webAppContextSetup((WebApplicationContext) context)
//                .alwaysDo(prepareJackson(objectMapper))
//                .alwaysDo(commonDocumentation())
//                .apply(documentationConfiguration(restDocumentation)
//                        .uris()
//                        .withScheme(HTTP)
//                        .withHost(LOCALHOST)
//                        .withPort(PORT)
//                        .and().snippets()
//                        .withDefaults(CliDocumentation.curlRequest(),
//                                HttpDocumentation.httpRequest(),
//                                HttpDocumentation.httpResponse(),
//                                AutoDocumentation.requestFields(),
//                                AutoDocumentation.responseFields(),
//                                AutoDocumentation.pathParameters(),
//                                AutoDocumentation.requestParameters(),
//                                AutoDocumentation.description(),
//                                AutoDocumentation.methodAndPath(),
//                                AutoDocumentation.section()))
//                .build();
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation)).build();
    }


    /**
     * Common documentation.
     *
     * @return
     */
    protected RestDocumentationResultHandler commonDocumentation() {
        return document(CLASS_METHOD_NAME,
                preprocessRequest(), commonResponsePreprocessor());
    }

    /**
     * Common response preprocessor
     *
     * @return
     */
    protected OperationResponsePreprocessor commonResponsePreprocessor() {
        return preprocessResponse(replaceBinaryContent(), limitJsonArrayLength(objectMapper),
                prettyPrint());
    }

    /**
     * Tests context loading.
     *
     */
    @Test
    void contextLoads() {
        assertNotNull(context);
       }
}

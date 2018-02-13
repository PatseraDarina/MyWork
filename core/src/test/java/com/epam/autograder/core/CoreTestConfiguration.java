package com.epam.autograder.core;

import com.epam.autograder.core.service.SubmissionService;
import com.epam.autograder.core.service.impl.SubmissionServiceImpl;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class CoreTestConfiguration {
    @Bean
    @Primary
    public SubmissionService submissionService() {
        return Mockito.mock(SubmissionServiceImpl.class);
    }
}

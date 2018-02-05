package com.epam.autograder.core;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.epam.autograder.core.config.StoreConfiguration;

/**
 * Class which configures DispatcherServlet
 *
 * @see SpringBootServletInitializer
 */
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CoreApplication.class, StoreConfiguration.class);
    }
}

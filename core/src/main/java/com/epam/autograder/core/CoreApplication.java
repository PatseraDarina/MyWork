package com.epam.autograder.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * Main class which represents  entry point of project core module.
 *
 * @see SpringBootApplication
 */
@EnableAutoConfiguration
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan(basePackages = {"com.epam.autograder.core.entity", "com.epam.autograder.core.repository", "com.epam.autograder.core.resource",
        "com.epam.autograder.core.service"})
public class CoreApplication {
    /**
     * Main method which launch project core module via Spring Boot
     *
     * @param args starting arguments for  application
     * @see SpringApplication
     */
    public static void main(String[] args) {
        SpringApplication.run(CoreApplication.class, args);
    }

}

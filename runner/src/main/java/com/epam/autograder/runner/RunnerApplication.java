package com.epam.autograder.runner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SpringBoot main class
 */
@SpringBootApplication
public class RunnerApplication {
    /**
     * @param args Input params
     */
    public static void main(String[] args) {
        SpringApplication.run(RunnerApplication.class, args);
    }
}

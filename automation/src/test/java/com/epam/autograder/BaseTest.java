package com.epam.autograder;

import com.epam.autograder.aqa.core.CoreRestClient;
import com.epam.autograder.aqa.core.RunnerRestClient;
import org.testng.annotations.BeforeClass;

public abstract class BaseTest {
    protected CoreRestClient coreRestClient;
    protected RunnerRestClient runnerRestClient;

    @BeforeClass
    public void setUp() {
        coreRestClient = new CoreRestClient(System.getProperty("coreURL"));
        runnerRestClient = new RunnerRestClient(System.getProperty("runnerURL"));
    }
}

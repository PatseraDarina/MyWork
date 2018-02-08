package com.epam.autograder;

import com.epam.autograder.aqa.core.CoreRestClient;
import org.testng.annotations.BeforeClass;

public abstract class BaseTest {
    protected CoreRestClient coreRestClient;

    @BeforeClass
    public void setUp() {
        coreRestClient = new CoreRestClient(System.getProperty("coreURL"));
    }
}

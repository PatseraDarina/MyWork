package com.epam.autograder.core;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.epam.autograder.core.config.StoreConfiguration;

/**
 * Class which tests CoreApplication class
 *
 * @see CoreApplication
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {CoreApplication.class, StoreConfiguration.class})
public class CoreApplicationTests {

    @Autowired
    private ApplicationContext ctx;

    /**
     * Context loads
     */
    @Test
    public void contextLoads() {
        assertNotNull("context loaded", ctx);
    }
}

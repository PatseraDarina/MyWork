package com.epam.autograder;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TestCase {
    @Test
    public void test1() {
        Assert.assertTrue(true);
    }

    @Test
    public void test2() {
        Assert.assertTrue(true);
    }

    @Test(dependsOnMethods = "test2")
    public void test3() {
        Assert.assertTrue(true);
    }
}

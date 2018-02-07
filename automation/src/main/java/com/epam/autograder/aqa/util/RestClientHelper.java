package com.epam.autograder.aqa.util;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;

import java.io.IOException;
import java.util.Arrays;

public class RestClientHelper {

    private RestClientHelper() {
    }

    private static int getStatusCode(HttpResponse response) {
        return response.getStatusLine().getStatusCode();
    }

    public static String getContent(HttpResponse response) {
        try {
            return EntityUtils.toString(response.getEntity());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void verifyStatusCode(HttpResponse response, Integer... expectedCodes) {
        int actualCode = getStatusCode(response);
        Assert.assertTrue(Arrays.asList(expectedCodes).contains(actualCode),
                String.format("FAIL to Verify Status Code. Actual: '%d', Expected: %s",
                        actualCode, Arrays.toString(expectedCodes)));
    }
}

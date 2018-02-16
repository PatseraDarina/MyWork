package com.epam.autograder.aqa.util;

import com.epam.autograder.aqa.annotation.Step;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Arrays;

public class RestClientHelper {

    private RestClientHelper() {
    }

    @Step
    public static void verifyAddressIsReachable(String url) {
        try {
            Assert.assertTrue(InetAddress.getByName(url).isReachable(30000),
                    "FAIL to Connect ".concat(url));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static int getStatusCode(HttpResponse response) {
        return response.getStatusLine().getStatusCode();
    }

    @Step
    public static String getContent(HttpResponse response) {
        try {
            return EntityUtils.toString(response.getEntity());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Step
    public static void verifyStatusCode(HttpResponse response, int expectedCode) {
        int actualCode = getStatusCode(response);
        Assert.assertEquals(expectedCode, actualCode,
                String.format("FAIL to Verify Status Code. Actual: '%d', Expected: %d",
                        actualCode, expectedCode));
    }

    @Step
    public static void verifyStatusCode(HttpResponse response, Integer... expectedCodes) {
        int actualCode = getStatusCode(response);
        Assert.assertTrue(Arrays.asList(expectedCodes).contains(actualCode),
                String.format("FAIL to Verify Status Code. Actual: '%d', Expected: %s",
                        actualCode, Arrays.toString(expectedCodes)));
    }
}

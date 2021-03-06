package com.epam.autograder.aqa.core;

import com.epam.autograder.aqa.annotation.Step;
import com.epam.autograder.aqa.util.JsonHelper;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;

import java.io.IOException;

public abstract class RestClient {
    private String baseUrl;

    public RestClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Step
    protected HttpResponse get(String path) {
        try {
            return Request.Get(this.baseUrl.concat(path)).execute().returnResponse();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Step
    protected HttpResponse post(String path, Object content) {
        try {
            return Request.Post(this.baseUrl.concat(path))
                    .bodyString(JsonHelper.toJson(content), ContentType.APPLICATION_JSON).execute().returnResponse();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Step
    protected HttpResponse delete(String path) {
        try {
            return Request.Delete(this.baseUrl.concat(path)).execute().returnResponse();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}

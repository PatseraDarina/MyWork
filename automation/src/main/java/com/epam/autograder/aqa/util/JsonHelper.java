package com.epam.autograder.aqa.util;

import com.google.gson.Gson;

public class JsonHelper {
    private static Gson gson;

    static {
        gson = new Gson();
    }

    private JsonHelper() {
    }

    public static String toJson(Object entity) {
        return gson.toJson(entity);
    }

    public static <T> T fromJson(String data, Class<T> type) {
        return gson.fromJson(data, type);
    }
}

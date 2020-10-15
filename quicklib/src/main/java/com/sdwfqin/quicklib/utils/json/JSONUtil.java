package com.sdwfqin.quicklib.utils.json;


import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

public class JSONUtil {
    public static <T> String toJson(T data) {
        return new GsonBuilder()
                .registerTypeAdapter(String.class, new StringNullAdapter())
                .create()
                .toJson(data);
    }

    public static <T> T from(String json, Type type) {
        return new GsonBuilder()
                .registerTypeAdapter(String.class, new StringNullAdapter())
                .create()
                .fromJson(json,type);
    }
}

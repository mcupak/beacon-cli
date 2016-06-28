package com.dnastack.beacon.cli.utils

/**
 * Gson helper to reuse one Gson instance.
 *
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
public class GsonHelper {
    public static final com.google.gson.Gson GSON = new com.google.gson.Gson()

    public static <T> T fromJson(String json, Class<T> clazz) {
        return GSON.fromJson(json, clazz)
    }

    public static String toJson(Object src) {
        return GSON.toJson(src)
    }
}

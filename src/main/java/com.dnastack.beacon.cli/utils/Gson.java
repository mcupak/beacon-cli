package com.dnastack.beacon.cli.utils;

import com.google.gson.GsonBuilder;

/**
 * Gson helper to reuse one Gson instance.
 *
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
public class Gson {
    public static final com.google.gson.Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static String pretty(Object src) {
        return GSON.toJson(src);
    }
}

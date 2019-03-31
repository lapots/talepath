package com.lapots.breed.backend.util;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.nio.file.Files;
import java.nio.file.Path;

import lombok.experimental.UtilityClass;

/**
 * Utils for Gson manipulations.
 */
@UtilityClass
public class GsonUtils {

    private static final Gson GSON = new Gson(); // singleton

    /**
     * Converts {@link JsonReader} to object.
     * @param input json reader
     * @param clazz output class
     * @param <T> output type
     * @return object instance
     */
    public <T> T toObject(JsonReader input, Class<T> clazz) {
        return GSON.fromJson(input, clazz);
    }

    /**
     * Converts data from path file to object.
     * @param file file
     * @param clazz output class
     * @param <T> output type
     * @return object instance
     */
    public <T> T toObject(Path file, Class<T> clazz) {
        JsonReader reader = pathResourceToReader(file);
        return toObject(reader, clazz);
    }

    /**
     * Readers file from the path into {@link JsonReader} object.
     * @param file file
     * @return json reader
     */
    public JsonReader pathResourceToReader(Path file) {
        try {
            return new JsonReader(Files.newBufferedReader(file));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

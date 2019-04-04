package com.lapots.breed.backend.util;

import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

/**
 * Wrapper for JSONAssert to handle exception.
 */
public class JsonAssertWrapper {
    /**
     * Asserts strict equality.
     * @param expected expected
     * @param actual actual
     */
    public static void assertEquals(String expected, String actual) {
        try {
            JSONAssert.assertEquals(expected, actual, JSONCompareMode.STRICT);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}

package com.lapots.breed.util;

import java.util.Optional;

import lombok.experimental.UtilityClass;

@UtilityClass
public class OptionalUtils {
    /**
     * Returns is not present condition.
     * @param optional optional
     * @param <T> type of optional
     * @return true if not present, false otherwise
     */
    public <T> boolean isNotPresent(Optional<T> optional) {
        return !optional.isPresent();
    }

}

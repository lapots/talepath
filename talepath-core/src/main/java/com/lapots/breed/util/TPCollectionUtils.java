package com.lapots.breed.util;

import java.util.Collection;
import java.util.Optional;

import lombok.experimental.UtilityClass;

/**
 * Collection utils.
 */
@UtilityClass
public class TPCollectionUtils {

    /**
     * Returns first element from the collection.
     * @param collection collection of any type
     * @param <T> collection item type
     * @return first element
     */
    public <T> Optional<T> getFirstElement(Collection<T> collection) {
        return collection.stream().findFirst();
    }
}

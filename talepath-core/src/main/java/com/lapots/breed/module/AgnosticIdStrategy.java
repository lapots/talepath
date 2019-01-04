package com.lapots.breed.module;

import com.lapots.breed.domain.StoryCharacter;
import com.lapots.breed.util.AgnosticIdUtils;
import com.lapots.breed.util.TPCollectionUtils;

import org.neo4j.ogm.id.IdStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Id strategy generation for {@link StoryCharacter}.
 */
public class AgnosticIdStrategy implements IdStrategy {
    private static final Logger LOGGER = LoggerFactory.getLogger(AgnosticIdStrategy.class);

    @Override
    public Object generateId(Object entity) {
        StoryCharacter storyCharacter = (StoryCharacter) entity;
        LOGGER.debug("Generating ID for object: {}", storyCharacter);
        var schema = TPCollectionUtils.getFirstElement(storyCharacter.getLabels()).get(); // labels should exist
        return AgnosticIdUtils.generateId(schema, storyCharacter.getName());
    }
}

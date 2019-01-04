package com.lapots.breed.module;

import com.lapots.breed.domain.StoryCharacter;
import com.lapots.breed.util.AgnosticIdUtils;
import com.lapots.breed.util.TPCollectionUtils;

import org.apache.commons.lang3.StringUtils;
import org.neo4j.ogm.session.event.Event;
import org.neo4j.ogm.session.event.EventListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Listener that sets character identifier for {@link StoryCharacter} entities.
 */
public class AddCharacterIdListener extends EventListenerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddCharacterIdListener.class);

    @Override
    public void onPreSave(Event event) {
        StoryCharacter storyCharacter = (StoryCharacter) event.getObject();
        if (StringUtils.isBlank(storyCharacter.getAgnosticId())) {
            LOGGER.debug("Generating ID for object: {}", storyCharacter);
            var schema = TPCollectionUtils.getFirstElement(storyCharacter.getLabels()).get(); // labels should exist
            var id = AgnosticIdUtils.generateId(schema, storyCharacter.getName());
            storyCharacter.setAgnosticId(id);
        }
    }
}

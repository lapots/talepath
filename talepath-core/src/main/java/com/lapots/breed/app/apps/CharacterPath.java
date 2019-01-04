package com.lapots.breed.app.apps;

import com.lapots.breed.domain.StoryCharacter;
import com.lapots.breed.util.AgnosticIdUtils;
import com.lapots.breed.util.OptionalUtils;
import com.lapots.breed.util.TPCollectionUtils;

import org.jooby.Jooby;
import org.neo4j.ogm.cypher.ComparisonOperator;
import org.neo4j.ogm.cypher.Filter;
import org.neo4j.ogm.session.SessionFactory;
import org.neo4j.ogm.transaction.Transaction.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * Represents actions available regarding characters of the story.
 */
public class CharacterPath extends Jooby {
    private static final Logger LOGGER = LoggerFactory.getLogger(CharacterPath.class);
    {
        get(req -> {
            LOGGER.info("Attempt to get all characters");
            var session = req.require(SessionFactory.class);
            return session.openSession().loadAll(StoryCharacter.class);
        });

        get("/:id", req -> {
            var id = req.param("id").value();

            LOGGER.info("Attempt to get character by id: {}", id);
            var session = req.require(SessionFactory.class);
            return session.openSession().loadAll(StoryCharacter.class,
                new Filter("agnosticId", ComparisonOperator.EQUALS, id));
        });

        post(req -> {
            var character = req.body(StoryCharacter.class);
            LOGGER.info("Attempt to stored character: {}", character);

            // TODO: introduce a way to find out the [story] of request
            character.getLabels().add("test");

            var session = req.require(SessionFactory.class).openSession();
            try (var tx = session.beginTransaction()) {
                Collection<StoryCharacter> lists = session.loadAll(StoryCharacter.class,
                    AgnosticIdUtils.findByIdQuery(character.getAgnosticId()));
                if (OptionalUtils.isNotPresent(TPCollectionUtils.getFirstElement(lists))) {
                    session.save(character);
                }
                tx.commit();
            }

            try (var tx = session.beginTransaction(Type.READ_ONLY)) {
                Collection<StoryCharacter> lists = session.loadAll(StoryCharacter.class,
                    AgnosticIdUtils.findByIdQuery(character.getAgnosticId()));
                tx.commit();

                return TPCollectionUtils.getFirstElement(lists).orElse(null);
            }
        });
    }
}

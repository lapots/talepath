package com.lapots.breed.graphql.wiring;

import com.lapots.breed.domain.StoryCharacter;
import graphql.schema.DataFetcher;

import java.util.Collections;

public class CharacterWiring {

    public DataFetcher singleCharacterFetcher = environment -> {
        var id = environment.getArgument("id").toString();

        var sc = new StoryCharacter();
        sc.setAgnosticId(id);
        sc.setName("Basic name");
        return sc;
    };

    public DataFetcher allCharactersFetcher = environment -> {
        var sc = new StoryCharacter();
        sc.setAgnosticId("random-id");
        sc.setName("Basic name");
        return Collections.singletonList(sc);
    };

    public DataFetcher upsertFetcher = environment -> {
        StoryCharacter input = environment.getArgument("character");

        StoryCharacter upsertChar = new StoryCharacter();
        upsertChar.setAgnosticId("upsert-" + input.getAgnosticId());
        upsertChar.setName("upsert-" + input.getName());
        return upsertChar;
    };
}

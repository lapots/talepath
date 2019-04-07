package com.lapots.breed.backend.service;

import com.lapots.breed.backend.data.PersonCharacter;
import com.lapots.breed.backend.service.api.ICharacterStorageServiceAsync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

/**
 * Implementation of {@link ICharacterStorageServiceAsync}.
 */
public class CharacterStorageServiceAsync extends AbstractVerticle implements ICharacterStorageServiceAsync {
    private static final Logger LOGGER = LoggerFactory.getLogger(CharacterStorageServiceAsync.class);
    private static final String USER_CHARACTERS_COLLECTION = "user-characters";

    private MongoClient mongoClient;

    /**
     * Constructor.
     * @param mongoClient mongo client
     */
    public CharacterStorageServiceAsync(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @Override
    public ICharacterStorageServiceAsync storePersonCharacter(PersonCharacter character,
                                                              Handler<AsyncResult<Void>> resultHandler) {
        JsonObject document = JsonObject.mapFrom(character);
        mongoClient.save(USER_CHARACTERS_COLLECTION, document, res -> {
            if (!res.succeeded()) {
                LOGGER.error("Failed to store document {} due to {}", document, res.cause().getCause());
                resultHandler.handle(Future.failedFuture(res.cause()));
            }
        });
        return this;
    }

    @Override
    public ICharacterStorageServiceAsync readPersonCharacter(String id,
                                                             Handler<AsyncResult<PersonCharacter>> resultHandler) {
        mongoClient.findOne(USER_CHARACTERS_COLLECTION, new JsonObject(), new JsonObject(), res -> {
            if (res.succeeded()) {
                resultHandler.handle(Future.succeededFuture(res.result().mapTo(PersonCharacter.class)));
            } else {
                resultHandler.handle(Future.failedFuture(res.cause()));
            }
        });
        return this;
    }

    @GenIgnore
    public static CharacterStorageServiceAsync create(MongoClient mongoClient) {
        return new CharacterStorageServiceAsync(mongoClient);
    }
}

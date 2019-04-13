package com.lapots.breed.backend.service.api;

import com.lapots.breed.backend.data.PersonCharacter;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * Interface for storing character.
 */
public interface ICharacterStorageServiceAsync {
    ICharacterStorageServiceAsync storePersonCharacter(PersonCharacter character,
                                                       Handler<AsyncResult<Void>> resultHandler);
    ICharacterStorageServiceAsync readPersonCharacter(String id, Handler<AsyncResult<PersonCharacter>> resultHandler);
}

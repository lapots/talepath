package com.lapots.breed.backend.service.api;

import com.lapots.breed.backend.data.PersonCharacter;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * Interface for storing character.
 */
@ProxyGen
@VertxGen
public interface ICharacterStorageServiceAsync {
    @Fluent
    ICharacterStorageServiceAsync storePersonCharacter(PersonCharacter character,
                                                       Handler<AsyncResult<Void>> resultHandler);
    @Fluent
    ICharacterStorageServiceAsync readPersonCharacter(String id, Handler<AsyncResult<PersonCharacter>> resultHandler);
}

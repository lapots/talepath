package com.lapots.breed.ui;

import com.lapots.breed.backend.data.PersonCharacter;
import com.lapots.breed.backend.service.CharacterStorageServiceAsync;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.Router;

/**
 * Demo verticle.
 */
public class SampleRestVerticle extends AbstractVerticle {

    @Override
    public void start(Future<Void> startFuture) {
        MongoClient mongoClient = MongoClient.createShared(vertx, new JsonObject()
            .put("connection_string", "mongodb://localhost:27017")
            .put("db_name", "talepath-store"));

        CharacterStorageServiceAsync.create(mongoClient);

        ServiceBinder binder = new ServiceBinder(vertx);

        Router router = Router.router(vertx);
        router.route("/").handler(routingContext -> {
            HttpServerResponse response = routingContext.response();
            response
                .putHeader("content-type", "text/html")
                .end("<h1>Sample Vert.x 3 application</h1>");
        });
        router.route("/api/character").handler(routingContext -> {
            HttpServerResponse response = routingContext.response();
            response
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encodePrettily(new PersonCharacter("p_id", "Jack", 20)));
        });
        vertx
            .createHttpServer()
            .requestHandler(router)
            .listen(
                config().getInteger("http.port", 8080),
                result -> {
                    if (result.succeeded()) {
                        startFuture.complete();
                    } else {
                        startFuture.fail(result.cause());
                    }
                }
            );
    }
}

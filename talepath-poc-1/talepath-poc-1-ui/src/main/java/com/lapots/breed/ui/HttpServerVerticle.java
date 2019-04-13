package com.lapots.breed.ui;

import com.lapots.breed.backend.data.PersonCharacter;

import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

/**
 * Http server verticle.
 */
public class HttpServerVerticle extends AbstractVerticle {
    private static final String HTTP_PORT_OPTION = "http.port";

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        ConfigStoreOptions resourceStore = new ConfigStoreOptions()
            .setType("file")
            .setConfig(new JsonObject().put("path", "conf/talepath-server.json"));
        ConfigRetrieverOptions options = new ConfigRetrieverOptions()
            .addStore(resourceStore);
        ConfigRetriever retriever = ConfigRetriever.create(vertx, options);
        retriever.getConfig(ar -> {
            if (ar.failed()) {
                startFuture.fail(new RuntimeException("Failed to retrieve configuration"));
            } else {
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
                JsonObject config = ar.result();
                vertx.createHttpServer()
                    .requestHandler(router)
                    .listen(
                        config.getInteger(HTTP_PORT_OPTION),
                        result -> {
                            if (result.succeeded()) {
                                startFuture.complete();
                            } else {
                                startFuture.fail(result.cause());
                            }
                        });
            }
        });
    }
}

package com.lapots.breed.ui;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;

/**
 * Demo verticle.
 */
public class SampleRestVerticle extends AbstractVerticle {

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        Router router = Router.router(vertx);
        router.route("/").handler(routingContext -> {
            HttpServerResponse response = routingContext.response();
            response
                .putHeader("content-type", "text/html")
                .end("<h1>Sample Vert.x 3 application</h1>");
        });
        vertx
            .createHttpServer()
            .requestHandler(router::accept)
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
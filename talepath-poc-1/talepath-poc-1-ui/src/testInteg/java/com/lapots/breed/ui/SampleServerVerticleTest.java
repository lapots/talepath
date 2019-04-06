package com.lapots.breed.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;

/**
 * Simple integration test for server.
 */
@ExtendWith(VertxExtension.class)
public class SampleServerVerticleTest {

    @BeforeEach
    void prepare(Vertx vertx, VertxTestContext testContext) {
        vertx.deployVerticle(SampleRestVerticle.class.getCanonicalName(), testContext.completing());
    }

    @Test
    @DisplayName("Test that server started")
    void checkServerHasStarted(Vertx vertx, VertxTestContext testContext) {
        WebClient webClient = WebClient.create(vertx);
        webClient.get(8080, "localhost", "/")
            .as(BodyCodec.string())
            .send(testContext.succeeding(response -> testContext.verify(() -> {
                assertEquals(200, response.statusCode());
                assertTrue(response.body().length() > 0);
                assertTrue(response.body().contains("Sample Vert.x 3 application"));
                testContext.completeNow();})));
    }
}

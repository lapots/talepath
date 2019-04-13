package com.lapots.breed.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;

/**
 * Tests for {@link HttpServerVerticle}.
 */
    @ExtendWith(VertxExtension.class)
    public class HttpServerVerticleIntegrationTest {
        private static final String HTTP_PORT_OPTION = "http.port";

        @Test
        public void startServerTest(Vertx vertx, VertxTestContext testContext) {
            ConfigStoreOptions resourceStore = new ConfigStoreOptions()
                .setType("file")
                .setConfig(new JsonObject().put("path", "conf/talepath-server.json"));
            ConfigRetrieverOptions options = new ConfigRetrieverOptions()
                .addStore(resourceStore);
            ConfigRetriever retriever = ConfigRetriever.create(vertx, options);
            retriever.getConfig(ar -> {
                if (ar.failed()) {
                    testContext.failNow(new RuntimeException("Failed to retrieve configuration"));
                } else {
                    JsonObject config = ar.result();
                    DeploymentOptions deploymentOptions = new DeploymentOptions().setConfig(config);
                    vertx.deployVerticle(new HttpServerVerticle(), deploymentOptions, testContext.succeeding(id -> {
                        WebClient webClient = WebClient.create(vertx);
                        webClient.get(config.getInteger(HTTP_PORT_OPTION), "localhost", "/")
                            .as(BodyCodec.string())
                            .send(testContext.succeeding(response -> testContext.verify(() -> {
                                assertEquals(200, response.statusCode());
                                assertTrue(response.body().length() > 0);
                                assertTrue(response.body().contains("Sample Vert.x 3 application"));
                                testContext.completeNow();})));
                    }));
                }
            });
        }
    }

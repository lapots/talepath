package com.lapots.breed.ui;

import static org.assertj.core.api.Assertions.assertThat;

import static io.restassured.RestAssured.get;

import com.lapots.breed.backend.data.PersonCharacter;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.restassured.RestAssured;
import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;

/**
 * Sample test for REST.
 */
@ExtendWith(VertxExtension.class)
public class HttpServerRestVerticleIntegrationTest {
    private static final String ID = "p_id";
    private static final String NAME = "Jack";
    private static final int AGE = 20;
    private static final String HTTP_PORT_OPTION = "http.port";

    @BeforeAll
    static void configureRestAssured(Vertx vertx, VertxTestContext testContext) {
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
                RestAssured.baseURI = "http://localhost";
                RestAssured.port = config.getInteger(HTTP_PORT_OPTION);
            }
        });
        vertx.deployVerticle(new HttpServerVerticle(), testContext.completing());
    }

    @Test
    @DisplayName("Test GET response")
    void checkGetCharacter() {
        PersonCharacter result = get("/api/character")
            .thenReturn()
            .as(PersonCharacter.class);
        assertThat(result.getId()).isEqualTo(ID);
        assertThat(result.getName()).isEqualTo(NAME);
        assertThat(result.getAge()).isEqualTo(AGE);
    }

    @AfterAll
    static void unconfigureRestAssured() {
        RestAssured.reset();
    }
}

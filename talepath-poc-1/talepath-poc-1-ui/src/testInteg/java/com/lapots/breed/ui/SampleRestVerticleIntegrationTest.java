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
import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;

/**
 * Sample test for REST.
 */
@ExtendWith(VertxExtension.class)
public class SampleRestVerticleIntegrationTest {
    private static final String ID = "p_id";
    private static final String NAME = "Jack";
    private static final int AGE = 20;

    @BeforeAll
    static void configureRestAssured(Vertx vertx, VertxTestContext testContext) {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = Integer.getInteger("http.port", 8080);

        vertx.deployVerticle(SampleRestVerticle.class.getCanonicalName(), testContext.completing());
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

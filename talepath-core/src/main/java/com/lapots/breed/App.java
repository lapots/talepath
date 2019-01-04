package com.lapots.breed;

import com.lapots.breed.app.TalePath;
import com.lapots.breed.module.Neo4jOGM;

import org.jooby.Jooby;
import org.jooby.handlers.CorsHandler;
import org.jooby.json.Jackson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main application.
 */
public class App extends Jooby {
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    {
        onStarted(() -> {
           LOGGER.info("JVM vendor: {}", System.getProperty("java.vm.vendor"));
           LOGGER.info("JVM specification: {}", System.getProperty("java.vm.specification.version"));
        });

        use(new Jackson());
        use("*", new CorsHandler());
        use(new Neo4jOGM());

        use("nodes", new TalePath());
    }
}

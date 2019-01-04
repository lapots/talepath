package com.lapots.breed.module;

import com.google.inject.Binder;
import com.typesafe.config.Config;

import org.jooby.Env;
import org.jooby.Jooby.Module;
import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.SessionFactory;

/**
 * Module for Neo4j OGM support.
 */
public class Neo4jOGM implements Module {
    @Override
    public void configure(Env env, Config conf, Binder binder) throws Throwable {
        var config = new Configuration
            .Builder()
            .uri(conf.getString("db.url"))
            .credentials(conf.getString("db.user"), conf.getString("db.password"));
        var sessionFactory = new SessionFactory(config.build(), "com.lapots.breed.domain");
        binder.bind(SessionFactory.class).toInstance(sessionFactory);
    }
}

package com.lapots.breed.util;

import org.neo4j.ogm.cypher.ComparisonOperator;
import org.neo4j.ogm.cypher.Filter;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AgnosticIdUtils {

    /**
     * Generates id.
     * @param story story name or schema name
     * @param name entity name for which id will be generated
     * @return generated id
     */
    public String generateId(String story, String name) {
        return String.format("story_%s_character_%s", story, name);
    }

    /**
     * Generates filter query for search.
     * @param agnosticId agnostic id
     * @return filter query
     */
    public Filter findByIdQuery(String agnosticId) {
        return new Filter("agnosticId", ComparisonOperator.EQUALS, agnosticId);
    }
}

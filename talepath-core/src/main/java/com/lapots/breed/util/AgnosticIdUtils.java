package com.lapots.breed.util;

import org.neo4j.ogm.cypher.ComparisonOperator;
import org.neo4j.ogm.cypher.Filter;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AgnosticIdUtils {

    /**
     * Generates id.
     *
     * Example:
     *  #1
     *      Input
     *          story = Overlord
     *          character = Albedo
     *      Output
     *          id = story_overlord_character_albedo
     *   #2
     *      Input
     *          story = Overlord
     *          character = Ainz Ooal Gown
     *      Output
     *          id = story_overlord_character_ainz_ooal_gown
     *
     *  TODO: investigate the way to update names as for id it uses it as a parameter
     *
     * @param story story name or schema name
     * @param name entity name for which id will be generated
     * @return generated id
     */
    public String generateId(String story, String name) {
        return String.format("story_%s_character_%s", story, name)
            .replaceAll(" ", "_")
            .toLowerCase();
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

package com.lapots.breed.domain;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;

import lombok.Data;

/**
 * Common graph object.
 */
@Data
abstract class GraphObject {
    @Id
    @GeneratedValue
    private Long id;
}

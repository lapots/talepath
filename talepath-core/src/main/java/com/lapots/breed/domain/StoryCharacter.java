package com.lapots.breed.domain;

import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.Labels;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Represents any character that is the part of the story.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NodeEntity
public class StoryCharacter extends GraphObject {
    @Id
    @Index(unique = true)
    private String agnosticId;
    private String name;

    @Relationship(type = "FAMILIAR_WITH")
    private Set<StoryCharacter> acquaintances;

    @Labels
    private Set<String> labels = new HashSet<>();

    // Neo4j requires constructor
    public StoryCharacter() {}
}

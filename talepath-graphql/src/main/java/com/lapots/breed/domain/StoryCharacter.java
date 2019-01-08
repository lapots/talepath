package com.lapots.breed.domain;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * I don't use shared domain practice.
 */
@Data
public class StoryCharacter {
    private String agnosticId;
    private String name;

    private Set<StoryCharacter> acquaintances;
    private Set<String> labels = new HashSet<>();
}

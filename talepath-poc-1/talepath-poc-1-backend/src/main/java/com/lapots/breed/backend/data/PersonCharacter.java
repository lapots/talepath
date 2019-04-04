package com.lapots.breed.backend.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.javers.core.metamodel.annotation.TypeName;

/**
 * Configurable entity for user.
 */
@TypeName("ConfigurablePersonCharacter")
@Data
@AllArgsConstructor
public class PersonCharacter {
    private String id;
    private String name;
    private int age;
}

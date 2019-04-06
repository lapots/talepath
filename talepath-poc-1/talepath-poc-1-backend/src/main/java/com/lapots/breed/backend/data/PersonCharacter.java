package com.lapots.breed.backend.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.javers.core.metamodel.annotation.Id;
import org.javers.core.metamodel.annotation.TypeName;

/**
 * Configurable entity for user.
 */
@TypeName("ConfigurablePersonCharacter")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonCharacter {
    @Id
    private String id;
    private String name;
    private int age;
}

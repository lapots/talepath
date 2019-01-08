package com.lapots.breed.graphql;

import com.lapots.breed.graphql.wiring.CharacterWiring;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.*;

import java.io.File;

public class GraphQLSchemasLoader {

    public GraphQL readSampleCharacterPath() {
        File schema = new File(this.getClass().getResource("/graphql/characterPath.graphqls").getFile());
        TypeDefinitionRegistry typeDefinitionRegistry = new SchemaParser().parse(schema);

        RuntimeWiring runtimeWiring = buildWiring();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);

        return GraphQL.newGraphQL(graphQLSchema).build();
    }

    private RuntimeWiring buildWiring() {
        CharacterWiring characterWiring = new CharacterWiring();

        return RuntimeWiring.newRuntimeWiring()
                .type(TypeRuntimeWiring.newTypeWiring("Query")
                    .dataFetcher("single", characterWiring.singleCharacterFetcher))
                .type(TypeRuntimeWiring.newTypeWiring("Character")
                    .typeName("StoryCharacter"))
                .build();
    }
}

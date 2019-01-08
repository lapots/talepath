package com.lapots.breed;

import com.lapots.breed.graphql.GraphQLSchemasLoader;
import graphql.ExecutionInput;
import graphql.GraphQL;

import java.util.Collections;
import java.util.Map;

/**
 * Main entry point.
 *
 */
public class Main {

    public static void main(String[] args) {
        GraphQL graphQL = new GraphQLSchemasLoader().readSampleCharacterPath();

        String query = "{ single(id: \"12341\") { id name acquaintances { id } labels } }";
        Map<String, Object> variables = Collections.emptyMap();

        ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                .query(query)
                .variables(variables)
                .operationName("single")
                .build();

        System.out.println(graphQL.execute(executionInput).toSpecification());
    }
}

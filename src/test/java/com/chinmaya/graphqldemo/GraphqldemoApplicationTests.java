package com.chinmaya.graphqldemo;

import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.PropertyDataFetcher;
import graphql.schema.StaticDataFetcher;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static graphql.schema.idl.RuntimeWiring.newRuntimeWiring;

@SpringBootTest
class GraphqldemoApplicationTests {

	@Test
	void contextLoads() {
//		String schema = "type Query{hello: String}";

		String nameSchema = "type Query{name: String}";
		SchemaParser schemaParser = new SchemaParser();
		TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(nameSchema);
		RuntimeWiring runtimeWiring = newRuntimeWiring()
				.type("Query", builder -> builder.dataFetcher("name",  new StaticDataFetcher("Chinmaya Panigrahi")))
				.build();

		SchemaGenerator schemaGenerator = new SchemaGenerator();
		GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);

		GraphQL build = GraphQL.newGraphQL(graphQLSchema).build();
		ExecutionResult executionResult = build.execute("{name}");
		System.out.println(executionResult.getData().toString());

//		SchemaParser schemaParser = new SchemaParser();
//		TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(schema);

//		RuntimeWiring runtimeWiring = newRuntimeWiring()
//				.type("Query", builder -> builder.dataFetcher("hello", new StaticDataFetcher("world")))
//				.build();

//		SchemaGenerator schemaGenerator = new SchemaGenerator();
//		GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
//
//		GraphQL build = GraphQL.newGraphQL(graphQLSchema).build();
//		ExecutionResult executionResult = build.execute("{hello}");
//
//		System.out.println(executionResult.getData().toString());
		// Prints: {hello=world}
	}

}

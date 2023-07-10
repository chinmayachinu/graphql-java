package com.chinmaya.graphqldemo;

import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.kickstart.tools.SchemaParser;
import graphql.kickstart.tools.SchemaParserBuilder;
import graphql.schema.GraphQLSchema;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class GraphqldemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraphqldemoApplication.class, args);
    }

    @Bean
    ApplicationRunner applicationRunner(QueryResolver queryResolver, BookResolver bookResolver) {

        SchemaParserBuilder file = SchemaParser
                .newParser()
                .file("graphql/schema.graphqls")
                .resolvers(queryResolver, bookResolver);

        System.out.println("Schema parser builder => " + file);
        SchemaParser schemaParser = file.build();
        System.out.println("Schema parser => " + schemaParser);

        GraphQLSchema graphQLSchema = schemaParser.makeExecutableSchema();
        GraphQL build = GraphQL.newGraphQL(graphQLSchema).build();
        ExecutionResult executionResult = build.execute("""
                query {
                   books {
                     id
                     authorId
                     title
                     coverImage
                     pages
                     releaseDate
                     isbn
                   }
                 }
                """);

        System.out.println(executionResult.getData().toString());

        return args -> System.out.println("application started");
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

}

package com.chinmaya.graphqldemo;

import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.kickstart.tools.GraphQLResolver;
import graphql.kickstart.tools.SchemaParser;
import graphql.kickstart.tools.SchemaParserBuilder;
import graphql.schema.GraphQLSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootApplication
public class GraphqldemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraphqldemoApplication.class, args);
    }

    @Bean
    ApplicationRunner applicationRunner(Query query, BookResolver bookResolver) {

        SchemaParserBuilder file = SchemaParser
                .newParser()
                .file("graphql/schema.graphqls")
                .resolvers(query, bookResolver);

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

    record Book(int id, int authorId, String title, String coverImage, int pages, String releaseDate, String isbn) {
    }

    @Component
    static class BookResolver implements GraphQLResolver<Book> /* This class is a resolver for the Book "Data Class" */ {

        public BookResolver() {
        }

//        public BookResolver(AuthorRepository authorRepository) {
//            this.authorRepository = authorRepository;
//        }

//        public Author author(Book book) {
//            return authorRepository.findById(book.getAuthorId());
//        }
    }

    @Component
    class Query implements GraphQLQueryResolver {

        @Autowired
        private final RestTemplate restTemplate;

        Query(RestTemplate restTemplate) {
            this.restTemplate = restTemplate;
        }

        public List<Book> books() {

            String url = "https://my-json-server.typicode.com/dmitrijt9/book-api-mock/books";
            List<Book> books = null;

            ResponseEntity<List<Book>> response = restTemplate.exchange(url, HttpMethod.GET, null,
                    new ParameterizedTypeReference<>() {
                    });

            if (response.getStatusCode().is2xxSuccessful()) {
                books = response.getBody();
                for (Book book : books) {
                    System.out.println(book.toString());
                }
            } else {
                System.out.println("Error: " + response.getStatusCode());
            }
            return books;
        }
    }

}

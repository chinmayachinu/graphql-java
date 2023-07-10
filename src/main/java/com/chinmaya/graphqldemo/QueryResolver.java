package com.chinmaya.graphqldemo;

import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
class QueryResolver implements GraphQLQueryResolver {

    @Autowired
    private final RestTemplate restTemplate;

    QueryResolver(RestTemplate restTemplate) {
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

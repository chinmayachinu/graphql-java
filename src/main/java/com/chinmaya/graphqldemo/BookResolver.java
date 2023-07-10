package com.chinmaya.graphqldemo;

import graphql.kickstart.tools.GraphQLResolver;
import org.springframework.stereotype.Component;

@Component
public class BookResolver implements GraphQLResolver<Book> /* This class is a resolver for the Book "Data Class" */ {

    public BookResolver() {
    }

//        public BookResolver(AuthorRepository authorRepository) {
//            this.authorRepository = authorRepository;
//        }

//        public Author author(Book book) {
//            return authorRepository.findById(book.getAuthorId());
//        }
}


package com.nbenja.jwtexample.domain;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookService {

    Mono<Book> findBook(String id);

    Mono<Book> createBook(Book book);

    Flux<Book> getBooks();

    Flux<Book> findBookByName(String name);
}

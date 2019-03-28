package com.nbenja.jwtexample.adapter.datastore;

import com.nbenja.jwtexample.domain.Book;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {

    @Query("{'name': ?0}")
    Flux<Book> findByName(String name);
}

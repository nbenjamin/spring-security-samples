package com.nbenja.jwtexample.config;

import com.nbenja.jwtexample.adapter.datastore.BookRepository;
import com.nbenja.jwtexample.adapter.datastore.UserRepository;
import com.nbenja.jwtexample.domain.Book;
import com.nbenja.jwtexample.domain.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import static java.util.Arrays.asList;

@Configuration
public class TestDataConfiguration {

    @Bean
    public CommandLineRunner commandLineRunner(BookRepository bookRepository, UserRepository userRepository) {

        return args -> {

            Flux<Book> books = Flux.just(
                    new Book(null,"Spring Seurity", "E-Book", "English", "I32SB", 39.99f),
                    new Book(null, "Spring Boot", "E-Book", "English", "44SSEE", 29.99f))
                    .flatMap( bookRepository::save);

            books.thenMany(bookRepository.findAll()).subscribe(System.out::println);

            Flux<User> users = Flux.just(
                    new User("user1", "pass123", asList("ROLE_USER"), "ryan", "adam"))
                    .flatMap(userRepository::save);

            users.thenMany(userRepository.findAll()).subscribe(System.out::println);

        };
    }

}

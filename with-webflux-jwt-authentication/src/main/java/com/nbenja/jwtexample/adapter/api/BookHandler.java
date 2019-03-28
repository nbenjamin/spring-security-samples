package com.nbenja.jwtexample.adapter.api;

import com.nbenja.jwtexample.domain.Book;
import com.nbenja.jwtexample.domain.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.reactive.function.server.ServerResponse.status;

@Component
public class BookHandler {

    private BookService bookService;

    public BookHandler(BookService bookService) {
        this.bookService = bookService;
    }

    public Mono<ServerResponse> getBooks(ServerRequest serverRequest){
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(bookService.getBooks(), Book.class);
    }

    public Mono<ServerResponse> createBook(ServerRequest serverRequest){
        Mono<Book> book = serverRequest.bodyToMono(Book.class);
        return book.flatMap(b -> status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(bookService.createBook(b), Book.class));
    }

    public Mono<ServerResponse> getBookByName(ServerRequest serverRequest) {
        Flux<Book> books = bookService.findBookByName(serverRequest.pathVariable("name"));

        return ok().contentType(MediaType.APPLICATION_JSON).body(books, Book.class);
    }

}

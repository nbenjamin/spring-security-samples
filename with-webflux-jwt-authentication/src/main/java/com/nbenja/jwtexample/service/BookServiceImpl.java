package com.nbenja.jwtexample.service;

import com.nbenja.jwtexample.adapter.datastore.BookRepository;
import com.nbenja.jwtexample.domain.Book;
import com.nbenja.jwtexample.domain.BookService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Mono<Book> findBook(String id) {
        return bookRepository.findById(id);
    }

    @Override
    public Mono<Book> createBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Flux<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Flux<Book> findBookByName(String name) {
        return bookRepository.findByName(name);
    }
}

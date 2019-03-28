package com.nbenja.jwtexample.domain;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<User> findUser(String username);

    Mono<User> save(User user);

    Flux<User> getUsers();
}

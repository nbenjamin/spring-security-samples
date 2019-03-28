package com.nbenja.jwtexample.service;

import com.nbenja.jwtexample.adapter.datastore.UserRepository;
import com.nbenja.jwtexample.domain.User;
import com.nbenja.jwtexample.domain.UserService;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<User> findUser(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Mono<User> save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Flux<User> getUsers() {
        return userRepository.findAll();
    }
}

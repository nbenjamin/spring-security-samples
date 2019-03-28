package com.nbenja.jwtexample.adapter.api;

import com.nbenja.jwtexample.domain.LoginRequest;
import com.nbenja.jwtexample.domain.LoginResponse;
import com.nbenja.jwtexample.domain.UserService;
import com.nbenja.jwtexample.security.JWTService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class AuthenticationHandler {

    private UserService userService;
    private JWTService jwtService;

    public AuthenticationHandler(UserService userService, JWTService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    public Mono<ServerResponse> login(ServerRequest serverRequest) {
        Mono<LoginRequest> loginRequest = serverRequest.bodyToMono(LoginRequest.class);

       return loginRequest.flatMap(u -> userService.findUser(u.getUsername()))
                .map(uu -> jwtService.generateToken(uu))
                .flatMap(t -> Mono.just(new LoginResponse(t)))
                .flatMap(r -> ok().body(fromObject(r))).switchIfEmpty(notFound().build());
    }
}

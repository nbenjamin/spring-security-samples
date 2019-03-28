package com.nbenja.jwtexample.config;

import com.nbenja.jwtexample.adapter.api.AuthenticationHandler;
import com.nbenja.jwtexample.adapter.api.BookHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
public class RouterConfiguration {

    @Bean
    public RouterFunction<ServerResponse> bookRouterFunction(BookHandler bookHandler) {
        return RouterFunctions.route(GET("/books"), bookHandler::getBooks)
                .andRoute(GET("/books/{name}"), bookHandler::getBookByName)
                .andRoute(POST("/books"), bookHandler::createBook);
    }

    @Bean
    public RouterFunction<ServerResponse> loginRouterFunction(AuthenticationHandler authenticationHandler) {
        return RouterFunctions.route(POST("/login"), authenticationHandler::login);
    }
}

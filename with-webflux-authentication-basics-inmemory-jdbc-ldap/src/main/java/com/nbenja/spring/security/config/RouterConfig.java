package com.nbenja.spring.security.config;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;


import com.nbenja.spring.security.api.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {

  @Bean
  public RouterFunction<ServerResponse> routerFunction(UserHandler userHandler) {
    return route().GET("/v2/orders", userHandler::getOrder).build();
  }

}

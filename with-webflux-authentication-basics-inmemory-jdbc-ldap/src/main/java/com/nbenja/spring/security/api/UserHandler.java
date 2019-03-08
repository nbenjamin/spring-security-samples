package com.nbenja.spring.security.api;

import static java.util.List.of;
import static org.springframework.web.reactive.function.server.ServerResponse.noContent;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static reactor.core.publisher.Mono.just;

import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class UserHandler {

  private Mono<List> items = just(of("Mac Book", "Iphone", " Galaxy S10"));

  public Mono<ServerResponse> getOrder(ServerRequest serverRequest) {
    return ok().body(items, List.class).switchIfEmpty(noContent().build());
  }
}

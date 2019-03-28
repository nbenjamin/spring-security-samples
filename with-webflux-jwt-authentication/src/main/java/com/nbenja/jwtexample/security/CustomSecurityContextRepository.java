package com.nbenja.jwtexample.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collections;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@ConditionalOnProperty(prefix = "jwt", name = "enable", havingValue = "true")
@Component
public class CustomSecurityContextRepository implements ServerSecurityContextRepository {

    private static final String BEARER = "Bearer ";
    private CustomAuthenticationManager authenticationManager;

    public CustomSecurityContextRepository(CustomAuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return Mono.empty();
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst(AUTHORIZATION))
                .filter(s -> s.startsWith(BEARER))
                .map(subs -> subs.substring(BEARER.length()))
                .flatMap(t -> Mono.just(new UsernamePasswordAuthenticationToken(t, t,
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")))))
                .flatMap(a -> authenticationManager.authenticate(a).map(SecurityContextImpl::new));
    }
}

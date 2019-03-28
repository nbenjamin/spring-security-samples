package com.nbenja.jwtexample.security;

import com.nbenja.jwtexample.domain.User;
import io.jsonwebtoken.Claims;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@ConditionalOnProperty(prefix = "jwt", name = "enable", havingValue = "true")
@Component
public class CustomAuthenticationManager implements ReactiveAuthenticationManager {

    private JWTService jwtProvider;

    public CustomAuthenticationManager(JWTService jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        User user = jwtProvider.getUserFromToken(authentication.getCredentials().toString());
        Claims claims = jwtProvider.getClaimsFromToken(authentication.getCredentials().toString());
        // Can do any validation here with User and Claims
        return Mono.just(authentication);

    }
}

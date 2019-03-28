package com.nbenja.jwtexample.security;

import com.nbenja.jwtexample.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;

@ConditionalOnProperty(prefix = "jwt", name = "enable", havingValue = "true")
@Component
public class JWTService {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.validity-milliseconds}")
    private long validity;

    public String generateToken(User user) {
        Date now  = new Date();
        Map<String, Object> header = new HashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");
        return Jwts.builder()
               // .claim("role", "ROLE_USER")
                .setSubject(user.getUsername())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime()+ validity))
                .signWith(HS256, getSecretKey())
                .setHeaderParams(header)
                .compact();
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(getSecretKey()).parseClaimsJws(token).getBody();
    }

    public User getUserFromToken(String token) {
        return new User(getClaimsFromToken(token).getSubject());
    }

    private String getSecretKey() {
        return Base64.getEncoder().encodeToString(secretKey.getBytes());
    }


}

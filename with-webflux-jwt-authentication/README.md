# Secure endpoints using Spring Security and JWT
This example shows how to secure your endpoints using simple spring security and also
using JWT. Spring security out of the box provides basic authentication by adding the following dependency your pom file.

### Dependency
```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
```

Once we have this in the classpath, spring security will generate a password for username `user`.
You can find something like this in the console.

```properties
Using generated security password: 91d82253-4161-433b-bdc5-5bcec31b13db
```

You can always configure your own username and password by adding the following properties 
in application.yml

```yaml
spring:
  security:
    user:
      name: admin
      password: admin
```

**Note : You can use this property `jwt.enable = true/false` to enable and disable JWT.**

### Test
You can use [postman](https://www.getpostman.com/) or curl. 

_Postman_
![Basic-Auth](https://github.com/nbenjamin/spring-security-samples/blob/master/with-webflux-jwt-authentication/docs/POSTMAN-BasicAuth.png)

_CURL_
```bash
curl -X GET \
  http://localhost:8086/books \
  -H 'Authorization: Basic YWRtaW46YWRtaW4=' \
  -H 'Content-Type: application/json' 
```

### Enable Security using JWT

JWT(JSON web token) Tokens - JSON Web Token (JWT) is a compact, URL-safe means of representing
claims to be transferred between two parties. The claims in a JWT are encoded as a JSON object that 
is used as the payload of a JSON Web Signature (JWS) structure or as the plaintext of a JSON Web Encryption (JWE) structure,
enabling the claims to be digitally signed or integrity protected with a Message Authentication Code (MAC) and/or encrypted.

**JWT Structure**
    
    1. Header
    2. Payload
    3. Signature
    
All these sections are base64 encoded (Note - it just encoded not encrypted)

**Header**
    ```json
    {
        "alg" : "HS256",
        "typ" : "JWT"
    }
    ```
    
**Payload** - This contains the claims, which is the statement about an entity and additional data.
Three types of claims

1. Registered claims: 
    Following are the main properties
    
    ```properties
    iss - issuer
    exp - expiration time
    sub - subject
    aud - audience
    nbf - not before
    iat - issued at
    jti - jwt id
    ``` 
2. Public claims: These are custom claim names, but make sure these are collision resistant.
3. Private claims: These are the custom claims to share information between parties.

**Signature** - Basically a hash of header(base64 encoded) and payload(base64 encoded) using secret.
Then finally encode the hash of the signature to get the final one.

```properties
“[Base64Encoded(HEADER)] . [Base64Encoded (PAYLOAD)] . [encoded(SIGNATURE)]”
```


for more details - [jwt.io](jwt.io)

### How to enable JWT with Spring Security

**ServerSecurityContextRepository**  - In spring reactive application `SecurityContext` will be
stored in this class. There are two types of implementations,

1. [NoOpServerSecurityContextRepository](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/web/server/context/NoOpServerSecurityContextRepository.html) - Make application stateless.
2. [WebSessionServerSecurityContextRepository](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/web/server/context/WebSessionServerSecurityContextRepository.html) - Store SecurityContext in webSession.

ex:
```java
 http...securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
 
 or 
 
 http...securityContextRepository(new WebSessionServerSecurityContextRepository())
```

Here is the custom configuration
```java

@Override
public Mono<SecurityContext> load(ServerWebExchange exchange) {
    return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst(AUTHORIZATION)) (1)
            .filter(s -> s.startsWith(BEARER)) (2)
            .map(subs -> subs.substring(BEARER.length())) (3)
            .flatMap(t -> Mono.just(new UsernamePasswordAuthenticationToken(t, t, 
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))))) (4)
            .flatMap(a -> authenticationManager.authenticate(a).map(SecurityContextImpl::new)); (5)
}
    
```
1. Get Authorization value from request Header.
2. Check if Authorization has `Bearer ` token
3. Remove prefix `Bearer ` from Authorization Token.
4. Create UsernamePasswordAuthenticationToken using the token.
5. Finally creat SecurityContext using Authentication.


**ServerWebExchange** - Can access HTTP request and response data. 
ex: form data from the body of the request, web session for the current request, etc.
`DefaultServerWebExchange` is the default implementation for `ServerWebExchange`.

### What next, ReactiveAuthenticationManager???

In Spring WebFlux, authentication is taken care of by `ReactiveAuthenticationManager`.

```java
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http,
                                                         CustomAuthenticationManager authenticationManager, 
                                                         CustomSecurityContextRepository securityContextRepository) {
        return http
                .formLogin().disable()
                .httpBasic().disable()
                .authenticationManager(authenticationManager)
                .securityContextRepository(securityContextRepository)
                .authorizeExchange().anyExchange().authenticated().and().build();
    }
}

```

### Test your service using JWT token

We have an endpoint for login, where this will take username and password,
if the given input is valid, the application will return a new token which can be
used for your authorization bearer token

#### Login endpoint
```properties
localhost:8086/login
```
**Request body**
```json
{
    "username": "user1",
    "password": "pass123"
}
```

if the user is valid then you will get something like this
```json
{
    "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMSIsImlhdCI6MTU1MzgwNDUwNCwiZXhwIjoxNTUzODA0NzM0fQ._F0L85jF4Hq1U_bdsV7YupIiqsqdfuhFqjlMinhF-KA"
}
```

Once you have this token you can access other endpoints using this token

Postman
![Basic-Auth](https://github.com/nbenjamin/spring-security-samples/blob/master/with-webflux-jwt-authentication/docs/POSTMAN-JWTToken.png)

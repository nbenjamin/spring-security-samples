# Enable Security for Actuator Endpoints
This example shows how to secure your actuator endpoints. 
By default, these endpoints are not exposed until you enable this 
property in `application.yaml/properties`

```yaml
management:
  endpoints:
    web:
      exposure:
        include: "*"
```

The above property will enable for all the endpoints from actuator.


#### Dependency for actuator and spring security

```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
```


Note : You can have multiple Security configurations in your application and 
can set the priority by setting the order, something like this.

```java
@Order(1)
```

#### How endpoints are secured
Let's see how to configure authorization for this endpoints.

```java
http.requestMatcher(EndpointRequest.toAnyEndpoint()) (1)
        .authorizeRequests() (2)
        .requestMatchers(EndpointRequest.to("health")).permitAll() (3)
        .anyRequest().authenticated() (4)
        .and()
        .httpBasic(); (5)
```

 - (1) - Request matcher for any endpoints from actuator endpoints.
 - (2) - Authorize any request going into the application.
 - (3) - Now add match request pattern to health endpoints and allow any request to that URL.
 - (4) - Rest all need to go through authentication.
 - (5) - enable HTTP basic authentication.

you can find [code](https://github.com/nbenjamin/spring-security-samples/tree/master/without-webflux-actuator-endpoint-security) here

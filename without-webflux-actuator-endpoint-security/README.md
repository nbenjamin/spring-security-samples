# Enable Security for Actuator Endpoints
This example shows how to secure your actuator endpoints. 
By the way by default these endpoints are not exposed until your this 
property in the `application.yaml/properties`

```yaml
management:
  endpoints:
    web:
      exposure:
        include: "*"
```

The above property will enable all the endpoints from actuator.


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


Note : You can multiple Security configuration in your application and 
can set the priority by setting the order, something like this.

```java
@Order(1)
```

#### How endpoints are secured
Lets see how to configure authorization for this endpoints.

```java
http.requestMatcher(EndpointRequest.toAnyEndpoint()) (1)
        .authorizeRequests() (2)
        .requestMatchers(EndpointRequest.to("health")).permitAll() (3)
        .anyRequest().authenticated() (4)
        .and()
        .httpBasic(); (5)
```

 - (1) - Request matcher for any endpoints from actuator endpoints.
 - (2) - Authorize any request going in to the application.
 - (3) - Now add match request pattern to HealthEndpoint and allow any request to that URL.
 - (4) - Rest all need to go through authentication.
 - (5) - enable http basic authentication.



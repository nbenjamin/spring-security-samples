### Spring Security In memory


#### Initialize User for non persistent users

```java

  @Bean
  public UserDetailsManager userDetailsManager() {
    return new InMemoryUserDetailsManager();
  }


  @Bean
  public CommandLineRunner commandLineRunner(UserDetailsManager userDetailsManager) {
    return args -> {
      String str;
      UserDetails admin = User.withUsername("admin")
          .password("{bcrypt}$2a$10$IfqjOKEC5zXmiIGISQG4uuNf7FMpKe.GfPXSkHF6TtdzuDJIHe0bu")
          .roles("USER").build();
      userDetailsManager.createUser(admin);
    };
  }
```
#### Generate encoded password

```java
    PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    encoder.encode("admin");
```
#### Prerequisites for JDBC profile
This project use docker for starting postgreSQL (database) and adminer (UI for database)

To Start both postgreSQL and Adminer, execute the following command

`docker-compose -f docker/docker-compose.yaml up`

#### Run Application

There are two profiles
  1. in-memory - will use inMemory UserDetailsManager.
  2. jdbc      - will postgreSQL, please docker-compose before executing this.


#### Running with profile `ldap`

```bash
curl http://localhost:8088/v1/orders -u ryan:ryan
```
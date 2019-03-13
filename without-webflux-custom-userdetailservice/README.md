# Custom userDetailService

`UserDetailsService` is specification around username, password and authorities. 
This core interface which loads user-specific data from configured back end service.
 

This example creates a custom `UserDetailsService` backed by mongodb.

#### UserDetailsService declaration

```java

@Component
public class CustomUserDetailsService implements UserDetailsService {

  private final PasswordEncoder passwordEncoder;

  private final UserRepository userRepository;

  public CustomUserDetailsService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user =  this.userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("Unable to find user - " + username));

    return withUsername(user.getUsername()).password(user.getPassword()).authorities(user.getAuthorities()).build();
  }
}

```

`PasswordEncoder` interface for encoding password. `NoOpPasswordEncoder` was the default `PasswordEncoder`
prior to Spring Security 5.0 however from Spring Security 5.0 onwards default `PasswordEncoder` is 
`BCryptPasswordEncoder`.

There are multiple ways you can declare the `PasswordEncoder`

```java
  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }
```
Above declare use the default `BCryptPasswordEncoder`

Available mappings
```java
		String encodingId = "bcrypt";
		Map<String, PasswordEncoder> encoders = new HashMap<>();
		encoders.put(encodingId, new BCryptPasswordEncoder());
		encoders.put("ldap", new org.springframework.security.crypto.password.LdapShaPasswordEncoder());
		encoders.put("MD4", new org.springframework.security.crypto.password.Md4PasswordEncoder());
		encoders.put("MD5", new org.springframework.security.crypto.password.MessageDigestPasswordEncoder("MD5"));
		encoders.put("noop", org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance());
		encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
		encoders.put("scrypt", new SCryptPasswordEncoder());
		encoders.put("SHA-1", new org.springframework.security.crypto.password.MessageDigestPasswordEncoder("SHA-1"));
		encoders.put("SHA-256", new org.springframework.security.crypto.password.MessageDigestPasswordEncoder("SHA-256"));
		encoders.put("sha256", new org.springframework.security.crypto.password.StandardPasswordEncoder());
```

#### User Model

In this example `User` model implements `UserDetails` interface. 
```java
@Document
public class User implements UserDetails {

  private final String username;
  private final String password;
  private Set<GrantedAuthority> authorities;
  private final String authority;
  private final boolean active;

  public User(String username, String password, boolean active, String authority) {
    this.username = username;
    this.password = password;
    this.authority = authority;
    this.active = active;
    this.authorities = Set.of(new SimpleGrantedAuthority(authority));
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.authorities;
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return this.username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return false;
  }

  @Override
  public boolean isAccountNonLocked() {
    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return false;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

}

```

#### Enable authentication for every request by extending WebSecurityConfigurerAdapter
By extending `WebSecurityConfigurerAdapter` you can do the following

  1. Set authentication restriction around the request
  2. Create User
  3. Can enable http basic and form based authentication.

```java
public class CustomerUserDetailsSecurityConfiguration extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.httpBasic();
    http.authorizeRequests().anyRequest().authenticated();
  }
}
```
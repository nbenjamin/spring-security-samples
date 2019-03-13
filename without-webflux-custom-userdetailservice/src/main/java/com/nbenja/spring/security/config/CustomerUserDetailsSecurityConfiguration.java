package com.nbenja.spring.security.config;

import com.nbenja.spring.security.api.UserExceptionHandler;
import com.nbenja.spring.security.domain.User;
import com.nbenja.spring.security.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class CustomerUserDetailsSecurityConfiguration extends WebSecurityConfigurerAdapter {

  /*  @Bean
  public PasswordEncoder passwordEncoder() {
    String type = "bcrypt";
    return new DelegatingPasswordEncoder(type, singletonMap(type, new BCryptPasswordEncoder()));
  }*/


  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

//  @Bean
//  PasswordEncoder passwordEncoder() {
//    return NoOpPasswordEncoder.getInstance();
//  }
//


  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.httpBasic();
    http.authorizeRequests().anyRequest().authenticated();
  }

  @Bean
  public CommandLineRunner commandLineRunner( UserRepository userRepository) {
    return args -> {
      List<User> users = List.of(new User("ryan", passwordEncoder().encode("password"), true, "USER"),
          new User("adam", passwordEncoder().encode("password"), true, "ADMIN"));
      users.stream().forEach(userRepository::save);

      userRepository.findAll().forEach(System.out::println);
    };
  }
}

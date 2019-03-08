package com.nbenja.spring.security.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

@Configuration
@EnableWebSecurity
@Profile("in-memory")
public class InMemorySecurityConfiguration {

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

}

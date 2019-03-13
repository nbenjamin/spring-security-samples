package com.nbenja.spring.security.security;

import static org.springframework.security.core.userdetails.User.withUsername;

import com.nbenja.spring.security.domain.User;
import com.nbenja.spring.security.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

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

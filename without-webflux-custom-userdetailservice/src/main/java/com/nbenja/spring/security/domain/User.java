package com.nbenja.spring.security.domain;

import java.util.Collection;
import java.util.Set;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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

  @Override
  public String toString() {
    return "User{" +
        "username='" + username + '\'' +
        ", password='" + password + '\'' +
        ", authorities=" + authorities +
        '}';
  }
}


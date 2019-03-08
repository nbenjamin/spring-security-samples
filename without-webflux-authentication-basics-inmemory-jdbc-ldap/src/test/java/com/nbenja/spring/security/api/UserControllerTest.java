package com.nbenja.spring.security.api;


import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserControllerTest {

  @Test
  public void encodePassword() {
    PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    System.out.println(encoder.encode("admin"));

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    System.out.println(bCryptPasswordEncoder.encode("ryan"));
  }
}
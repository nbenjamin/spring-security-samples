package com.nbenja.spring.security.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

/**
 * TODO need to register
 */
@Component
public class UserExceptionHandler implements AuthenticationFailureHandler {

  @Autowired
  private UserExceptionHandler userExceptionHandler;
  @Autowired
  private ObjectMapper objectMapper;

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException exception) throws IOException, ServletException {

    response.getOutputStream().print("{\"error\": \"Invalid credentials!\"}");
  }
}

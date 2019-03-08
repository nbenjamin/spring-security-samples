package com.nbenja.spring.security.api;

import static java.util.List.of;

import java.security.Principal;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/v1/orders")
public class UserController {

  private List<String> items = of("Mac Book", "Iphone", " Galaxy S10");


  @GetMapping
  public ResponseEntity getOrder(Principal principal) {
    return  new ResponseEntity(items, HttpStatus.OK);
  }
}

package com.nbenja.spring.security.oauth2.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class BookController {

    @GetMapping("/books")
    public String books(Model model){
        model.addAttribute("books", List.of(new Books("Keycloak", 29.99), new Books("Spring Security", 39.99)));
        return "books";
    }

    @GetMapping("/error")
    public String error(){
        return "error";
    }

}

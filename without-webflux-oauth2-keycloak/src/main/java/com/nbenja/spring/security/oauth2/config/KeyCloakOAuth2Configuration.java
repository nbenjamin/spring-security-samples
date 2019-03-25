package com.nbenja.spring.security.oauth2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class KeyCloakOAuth2Configuration /*extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.anonymous().disable().authorizeRequests().anyRequest().authenticated().and().oauth2Login();
    }
}
*/ {}
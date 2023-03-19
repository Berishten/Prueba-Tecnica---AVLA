package com.pabloescobar.pruebatecnica.Utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Bean
        public SecurityFilterChain configure(HttpSecurity http) throws Exception {
                http.authorizeRequests()
                        .antMatchers(HttpMethod.GET, "/**").permitAll()
                        .antMatchers(HttpMethod.POST, "/**").permitAll()
                        .antMatchers(HttpMethod.PUT, "/**").permitAll()
                        .antMatchers(HttpMethod.DELETE, "/**").permitAll()
                        .anyRequest().authenticated()
                        .and()
                        .csrf().disable();
                return http.build();
        }
}
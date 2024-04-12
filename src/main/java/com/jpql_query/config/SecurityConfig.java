package com.jpql_query.config;

import com.jpql_query.payload.JwtResponse;
import com.jpql_query.security.JetRequestFilter;
import com.jpql_query.security.JwtService;
import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
public class SecurityConfig {
    @Autowired
    private JetRequestFilter jetRequestFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().disable();
        http.addFilterBefore(jetRequestFilter, AuthorizationFilter.class);
        http.authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/api/v1/users/add", "/api/v1/users/login").permitAll()
                .requestMatchers(HttpMethod.GET,"/api/v1/properties","/api/v1/reviews/{propertyId}","/home").permitAll()
                .requestMatchers(HttpMethod.GET,"/api/v1/users/profile").hasRole("USER")
                .requestMatchers(HttpMethod.POST,"/api/v1/favourites/add/{propertyId}").hasAnyRole("USER","ADMIN")
                .requestMatchers(HttpMethod.GET,"/api/v1/reviews").hasAnyRole("USER","ADMIN")
                .anyRequest().authenticated();
        return http.build();
    }
}

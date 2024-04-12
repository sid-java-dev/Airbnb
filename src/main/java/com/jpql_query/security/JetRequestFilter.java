package com.jpql_query.security;

import com.jpql_query.entity.PropertyUser;
import com.jpql_query.repository.PropertyRepository;
import com.jpql_query.repository.PropertyUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

@Component
public class JetRequestFilter extends OncePerRequestFilter {
    private JwtService jwtService;
    private PropertyUserRepository userRepo;

    public JetRequestFilter(JwtService jwtService, PropertyUserRepository userRepo) {
        this.jwtService = jwtService;
        this.userRepo = userRepo;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader("Authorization");
        if(tokenHeader!=null && tokenHeader.startsWith("Bearer")){
            String token = tokenHeader.substring(7);
            String username = jwtService.getUsernameFromToken(token);
            Optional<PropertyUser> opUser = userRepo.findByUsername(username);
            if(opUser.isPresent()){
                PropertyUser propertyUser = opUser.get();
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(propertyUser, null, Collections.singleton(new SimpleGrantedAuthority(propertyUser.getUserRole())));
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }

        }
       filterChain.doFilter(request,response);
    }
}

package com.jpql_query.controller;

import com.jpql_query.entity.PropertyUser;
import com.jpql_query.payload.JwtResponse;
import com.jpql_query.payload.LoginDto;
import com.jpql_query.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
public class LoginController {
//    @Autowired
//    private SessionRegistry sessionRegistry;
    private LoginService loginService;
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }
@PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDto loginDto) {
    String token = loginService.loginUser(loginDto);
    if(token!=null){
       return new ResponseEntity<>(new JwtResponse(token),HttpStatus.OK);
    }
    return new ResponseEntity<>("Invalid Credentials", HttpStatus.OK);
    }

    @GetMapping("/profile")
    public PropertyUser getCurrentUser(@AuthenticationPrincipal PropertyUser propertyUser){
        return propertyUser;
    }
//    @GetMapping("/profile")
//    public List<PropertyUser> getCurrentUsers() {
//        List<Object> principals = sessionRegistry.getAllPrincipals();
//        return principals.stream()
//                .filter(principal -> principal instanceof UserDetails)
//                .map(principal -> (PropertyUser) principal)
//                .collect(Collectors.toList());
//    }

}

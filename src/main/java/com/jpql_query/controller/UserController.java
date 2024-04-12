package com.jpql_query.controller;

import com.jpql_query.entity.PropertyUser;
import com.jpql_query.payload.UserRequest;
import com.jpql_query.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
@PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody UserRequest userRequest) {
        PropertyUser propertyUser = userService.addUser(userRequest);
        if (propertyUser != null) {
            return new ResponseEntity<>(propertyUser, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Something went wrong",HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

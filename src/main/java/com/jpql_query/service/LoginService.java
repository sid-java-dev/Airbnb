package com.jpql_query.service;

import com.jpql_query.entity.PropertyUser;
import com.jpql_query.payload.LoginDto;
import com.jpql_query.repository.PropertyUserRepository;
import com.jpql_query.security.JwtService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {
    private JwtService jwtService;

    private PropertyUserRepository userRepo;

    public LoginService(JwtService jwtService, PropertyUserRepository userRepo) {
        this.jwtService = jwtService;
        this.userRepo = userRepo;
    }
    public String loginUser(LoginDto loginDto){
        Optional<PropertyUser> byUsername = userRepo.findByUsername(loginDto.getUsername());
        if(byUsername.isPresent()){
            PropertyUser user = byUsername.get();
            if(BCrypt.checkpw(loginDto.getPassword(),user.getPassword())){
                String token = jwtService.generateToken(user);
                return token;
            }

        }
        return null;
    }
}

package com.jpql_query.service;

import com.jpql_query.entity.PropertyUser;
import com.jpql_query.payload.UserRequest;
import com.jpql_query.repository.PropertyUserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private PropertyUserRepository userRepository;

    public UserService(PropertyUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public PropertyUser addUser(UserRequest userRequest) {
        PropertyUser user = new PropertyUser();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setUsername(userRequest.getUsername());
        user.setPassword(BCrypt.hashpw(userRequest.getPassword(), BCrypt.gensalt(5)));
        user.setUserRole(userRequest.getUserRole());
        return  userRepository.save(user);
    }
}

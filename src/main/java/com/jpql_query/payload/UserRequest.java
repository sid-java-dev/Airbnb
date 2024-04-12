package com.jpql_query.payload;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private Long id;
    private String userRole;
    private String password;
    private String email;
    private String username;
    private String lastName;
    private String firstName;
}

package com.jpql_query.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jpql_query.entity.PropertyUser;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    private static final String USER_NAME="username";
    @Value("${jwt.algorithm.key}")
    private String algorithmKey;
    @Value("${jwt.issuer}")
    private String issuer;
    @Value(("${jwt.expiry.Duration}"))
    private int expiryDuration;
    private Algorithm algorithm;
    @PostConstruct
    public void init(){
        algorithm=Algorithm.HMAC256(algorithmKey);
    }
    public String generateToken(PropertyUser user){
        return JWT.create()
                .withClaim(USER_NAME,user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+expiryDuration))
                .withIssuer(issuer)
                .sign(algorithm);

    }

    public String getUsernameFromToken(String token) {
        DecodedJWT decodedJWT = JWT.require(algorithm).withIssuer(issuer).build().verify(token);
        return decodedJWT.getClaim(USER_NAME).asString();
    }
}

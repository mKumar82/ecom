package com.ecom.UserService.jwt;

import com.ecom.UserService.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtToken {
    private static final String SECERET = "hgcghccghcvjhvjvjfvjvjccjghjvjvhgchchgchgchg";

    public static String generateToken(User user){
        return Jwts.builder()
                .setSubject(user.getId().toString())
//                .claim("role",user.getRole())
//                .claim("userId",user.getId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(Keys.hmacShaKeyFor(SECERET.getBytes()))
                .compact();
    }
}

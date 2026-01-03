package com.pranjal.user_service.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.pranjal.user_service.dtos.LoginRequest;
import com.pranjal.user_service.dtos.LoginResponse;
import com.pranjal.user_service.dtos.UserResponse;
import com.pranjal.user_service.entity.User;
import com.pranjal.user_service.exception.UserNotFoundException;
import com.pranjal.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw new BadCredentialsException("Incorrect Password");
        }

        String token = generateToken(user);

        return LoginResponse.builder()
                .token(token)
                .user(toResponse(user))
                .build();
    }

    public UserResponse toResponse(User user){
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .createdOn(user.getCreatedOn())
                .build();
    }

    private String generateToken(User user) {
        return JWT.create()
                .withSubject(user.getEmail())
                .withClaim("userId", user.getId())
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plus(jwtExpiration, ChronoUnit.SECONDS))
                .sign(Algorithm.HMAC256(jwtSecret));
    }
}

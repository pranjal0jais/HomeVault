package com.pranjal.user_service.controller;

import com.pranjal.user_service.dtos.LoginRequest;
import com.pranjal.user_service.dtos.LoginResponse;
import com.pranjal.user_service.dtos.RegisterRequest;
import com.pranjal.user_service.dtos.UserResponse;
import com.pranjal.user_service.service.AuthenticationService;
import com.pranjal.user_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser (@RequestBody @Valid RegisterRequest registerRequest) {
        UserResponse response = userService.createUser(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/user/id/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable String id,
                                                    @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(userService.getUserById(id, jwt.getClaimAsString("userId")));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody @Valid LoginRequest loginRequest){
        return ResponseEntity.ok(authenticationService.login(loginRequest));
    }
}

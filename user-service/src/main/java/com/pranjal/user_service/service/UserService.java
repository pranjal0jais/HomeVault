package com.pranjal.user_service.service;

import com.pranjal.user_service.dtos.RegisterRequest;
import com.pranjal.user_service.dtos.UserResponse;
import com.pranjal.user_service.entity.User;
import com.pranjal.user_service.exception.UserAlreadyExistsException;
import com.pranjal.user_service.exception.UserNotFoundException;
import com.pranjal.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse createUser(RegisterRequest userRequest) {

        if(userRepository.existsByEmail(userRequest.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists");
        }

        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .username(userRequest.getUsername())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .email(userRequest.getEmail())
                .createdOn(LocalDateTime.now())
                .build();
        userRepository.save(user);

        return toResponse(user);
    }

    public UserResponse getUserById(String id, String loggedInUserId) {
        if(!id.equals(loggedInUserId)) {
            throw new UserNotFoundException("User not found");
        }
        User user = userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("User not found"));

        return toResponse(user);
    }

    private UserResponse toResponse(User user){
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .createdOn(user.getCreatedOn())
                .build();
    }
}

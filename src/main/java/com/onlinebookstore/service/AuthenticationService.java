package com.onlinebookstore.service;

import org.hibernate.service.spi.ServiceException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.onlinebookstore.dto.AuthenticationRequest;
import com.onlinebookstore.dto.RegisterRequest;
import com.onlinebookstore.entity.User;
import com.onlinebookstore.enumerations.UserType;
import com.onlinebookstore.repository.UserRepository;
import com.onlinebookstore.security.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public String register(RegisterRequest request) {
        try {
            User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .userType(UserType.ADMIN)
                .build();

            userRepository.save(user);
            return "Registration successful";
        } catch (Exception e) {
            throw new ServiceException("An error occurred while processing your registration request. Please try again later.", e);
        }
    }

    public String authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + request.getEmail()));
            String jwtToken = jwtService.generateToken(user);
            return "Authentication successful: " + jwtToken;
        } catch (Exception e) {
            return "Authentication failed: " + e.getMessage();
        }
    }
    }

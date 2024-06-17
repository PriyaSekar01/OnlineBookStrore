package com.onlinebookstore.controller;

import org.hibernate.service.spi.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onlinebookstore.dto.AuthenticationRequest;
import com.onlinebookstore.dto.RegisterRequest;
import com.onlinebookstore.exception.AuthenticationServiceException;
import com.onlinebookstore.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication Management System")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest request) {
        try {
            String successMessage = authenticationService.register(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(successMessage);
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate user and generate token")
    public ResponseEntity<String> authenticateUser(@RequestBody AuthenticationRequest request) {
        try {
            String result = authenticationService.authenticate(request);
            return ResponseEntity.ok(result); // Response already contains JSON token
        } catch (AuthenticationServiceException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred. Please try again later.");
        }
    }
}

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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Api(value = "Authentication Management System", tags = "Authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @ApiOperation(value = "Register a new user")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest request) {
        try {
            String successMessage = authenticationService.register(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(successMessage);
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    @ApiOperation(value = "Authenticate user and generate token")
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
package com.onlinebookstore.controller;

import org.springframework.http.HttpHeaders;

import java.util.UUID;

import org.hibernate.service.spi.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onlinebookstore.dto.AuthenticationRequest;
import com.onlinebookstore.dto.RegisterRequest;
import com.onlinebookstore.exception.AuthenticationServiceException;
import com.onlinebookstore.response.Response;
import com.onlinebookstore.response.ResponseGenerator;
import com.onlinebookstore.response.TransactionContext;
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
    private final ResponseGenerator responseGenerator;

    @PostMapping("/register")
    public ResponseEntity<Response> registerUser(@RequestHeader HttpHeaders headers, @RequestBody RegisterRequest request) {
        TransactionContext context = responseGenerator.generateTransactionContext(headers);
        try {
            String successMessage = authenticationService.register(request);
            return responseGenerator.successResponse(context, successMessage, HttpStatus.CREATED);
        } catch (ServiceException e) {
            return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate user and generate token")
    public ResponseEntity<Response> authenticateUser(@RequestHeader HttpHeaders headers, @RequestBody AuthenticationRequest request) {
        TransactionContext context = responseGenerator.generateTransactionContext(headers);
        try {
            String result = authenticationService.authenticate(request);
            return responseGenerator.successResponse(context, result, HttpStatus.OK); // Response already contains JSON token
        } catch (AuthenticationServiceException e) {
            return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return responseGenerator.errorResponse(context, "An unexpected error occurred. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/user/{id}")
    public ResponseEntity<?>getuser(@PathVariable UUID id){
    	return authenticationService.getuser(id);
    }
}

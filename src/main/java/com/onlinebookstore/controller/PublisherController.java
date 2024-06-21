package com.onlinebookstore.controller;

import java.util.List;
import java.util.UUID;

import org.hibernate.service.spi.ServiceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onlinebookstore.dto.StoryRequest;
import com.onlinebookstore.entity.Comment;
import com.onlinebookstore.entity.Publisher;
import com.onlinebookstore.entity.Story;
import com.onlinebookstore.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import com.onlinebookstore.response.ResponseGenerator;
import com.onlinebookstore.response.TransactionContext;
import com.onlinebookstore.service.PublisherService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/publishers")
@RequiredArgsConstructor
@Tag(name = "Publisher", description = "Publisher Management System")
public class PublisherController {

    private final PublisherService publisherService;
    private final ResponseGenerator responseGenerator;

    
    @GetMapping("get/{publisherId}")
    @Operation(summary = "Get publisher details by ID")
    public ResponseEntity<Response> getPublisherById(@PathVariable UUID publisherId, @RequestHeader HttpHeaders headers) {
        TransactionContext context = responseGenerator.generateTransactionContext(headers);
        try {
            Publisher publisher = publisherService.getPublisherById(publisherId);
            return responseGenerator.successResponse(context, publisher, HttpStatus.OK);
        } catch (ServiceException e) {
            return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{publisherId}/comments")
    @Operation(summary = "Get comments for a publisher's stories")
    public ResponseEntity<Response> getCommentsForPublisher(@PathVariable UUID publisherId, @RequestHeader HttpHeaders headers) {
        TransactionContext context = responseGenerator.generateTransactionContext(headers);
        try {
            List<Comment> comments = publisherService.getCommentsForPublisher(publisherId);
            return responseGenerator.successResponse(context, comments, HttpStatus.OK);
        } catch (ServiceException e) {
            return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/stories/{publisherId}")
    @Operation(summary = "Add a new story")
    public ResponseEntity<Response> addStory(
        @PathVariable UUID publisherId, 
        @RequestBody StoryRequest storyRequest, 
        @RequestHeader HttpHeaders headers
    ) {
        TransactionContext context = responseGenerator.generateTransactionContext(headers);
        try {
            System.out.println("Publisher ID: " + publisherId);
            Story newStory = publisherService.addStory(publisherId, storyRequest);
            return responseGenerator.successResponse(context, newStory, HttpStatus.CREATED);
        } catch (ServiceException e) {
            return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
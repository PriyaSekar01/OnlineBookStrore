package com.onlinebookstore.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onlinebookstore.entity.Story;
import com.onlinebookstore.service.StoryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("publisher/stories")
@RequiredArgsConstructor
public class PublisherController {

    private final StoryService storyService;

    @PostMapping
    public ResponseEntity<Story> createStory(@RequestBody Story story) {
        Story createdStory = storyService.saveStory(story);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStory);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Story> getStoryById(@PathVariable UUID id) {
        Story story = storyService.getStoryById(id);
        if (story != null) {
            return ResponseEntity.ok(story);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Story> updateStory(@PathVariable UUID id, @RequestBody Story storyDetails) {
        Story updatedStory = storyService.updateStory(id, storyDetails);
        if (updatedStory != null) {
            return ResponseEntity.ok(updatedStory);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStory(@PathVariable UUID id) {
        boolean deleted = storyService.deleteStory(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Story>> getAllStories() {
        List<Story> stories = storyService.getAllStories();
        return ResponseEntity.ok(stories);
    }
}
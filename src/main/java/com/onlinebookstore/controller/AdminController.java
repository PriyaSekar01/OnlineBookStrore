package com.onlinebookstore.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("admin/stories")
@RequiredArgsConstructor
public class AdminController {

    private final StoryService storyService;

    @PostMapping
    public ResponseEntity<Story> createStory(@RequestBody Story story) {
        Story createdStory = storyService.saveStory(story);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStory);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Story> getStoryById(@PathVariable UUID id) {
        Story story = storyService.getStoryById(id);
        return ResponseEntity.ok(story);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Story> updateStory(@PathVariable UUID id, @RequestBody Story storyDetails) {
        Story updatedStory = storyService.updateStory(id, storyDetails);
        return ResponseEntity.ok(updatedStory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStory(@PathVariable UUID id) {
        storyService.deleteStory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Story>> getAllStories() {
        List<Story> stories = storyService.getAllStories();
        return ResponseEntity.ok(stories);
    }
}
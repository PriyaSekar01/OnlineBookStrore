package com.onlinebookstore.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onlinebookstore.entity.Story;
import com.onlinebookstore.service.StoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("user/stories")
@RequiredArgsConstructor
public class UserController {

    private final StoryService storyService;

    @GetMapping("/{id}")
    public ResponseEntity<Story> getStoryById(@PathVariable UUID id) {
        Story story = storyService.getPublishedStoryById(id);
        if (story != null) {
            return ResponseEntity.ok(story);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Story>> getAllPublishedStories() {
        List<Story> stories = storyService.getAllPublishedStories();
        return ResponseEntity.ok(stories);
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<Void> likeStory(@PathVariable UUID id) {
        storyService.likeStory(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/comment")
    public ResponseEntity<Void> commentOnStory(@PathVariable UUID id, @RequestBody String comment) {
        storyService.commentOnStory(id, comment);
        return ResponseEntity.ok().build();
    }
}



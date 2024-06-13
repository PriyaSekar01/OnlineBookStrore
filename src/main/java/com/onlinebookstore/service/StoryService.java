package com.onlinebookstore.service;

import java.util.List;
import java.util.UUID;

import com.onlinebookstore.entity.Story;
import com.onlinebookstore.exception.ResourceNotFoundException;

public interface StoryService {
    Story saveStory(Story story);
    Story getStoryById(UUID id) throws ResourceNotFoundException;
    Story updateStory(UUID id, Story storyDetails);
    boolean deleteStory(UUID id);
    List<Story> getAllStories();
    List<Story> getAllPublishedStories();
    Story getPublishedStoryById(UUID id);
    void likeStory(UUID id);
    void commentOnStory(UUID id, String comment);
    
}


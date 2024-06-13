package com.onlinebookstore.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.onlinebookstore.entity.Story;
import com.onlinebookstore.exception.ResourceNotFoundException;
import com.onlinebookstore.repository.StoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StoryServiceImpl implements StoryService {

    private final StoryRepository storyRepository;

    @Override
    public Story saveStory(Story story) {
        return storyRepository.save(story);
    }

    @Override
    public Story getStoryById(UUID id) {
        return storyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Story not found"));
    }

    @Override
    public Story updateStory(UUID id, Story storyDetails) {
        Story story = getStoryById(id);
        story.setTitle(storyDetails.getTitle());
        story.setAuthor(storyDetails.getAuthor());
        story.setGenre(storyDetails.getGenre());
        story.setContent(storyDetails.getContent());
        story.setLikes(storyDetails.getLikes());
        story.setComments(storyDetails.getComments());
        story.setPublished(storyDetails.isPublished());
        return storyRepository.save(story);
    }

    @Override
    public boolean deleteStory(UUID id) {
        storyRepository.deleteById(id);
		return false;
    }

    
    @Override
    public List<Story> getAllStories() {
        return storyRepository.findAll();
    }

    @Override
    public List<Story> getAllPublishedStories() {
        return storyRepository.findByIsPublishedTrue();
    }

    @Override
    public Story getPublishedStoryById(UUID id) {
        Story story = getStoryById(id);
        if (!story.isPublished()) {
            throw new ResourceNotFoundException("Story not found");
        }
        return story;
    }

    @Override
    public void likeStory(UUID id) {
        Story story = getStoryById(id);
        story.setLikes(story.getLikes() + 1);
        storyRepository.save(story);
    }

    @Override
    public void commentOnStory(UUID id, String comment) {
        Story story = getStoryById(id);
        story.getComments().add(comment);
        storyRepository.save(story);
    }
}


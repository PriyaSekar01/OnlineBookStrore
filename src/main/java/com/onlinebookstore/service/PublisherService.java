package com.onlinebookstore.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;

import com.onlinebookstore.dto.StoryRequest;
import com.onlinebookstore.entity.Comment;
import com.onlinebookstore.entity.Publisher;
import com.onlinebookstore.entity.Story;
import com.onlinebookstore.repository.PublisherRepository;
import com.onlinebookstore.repository.StoryRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PublisherService {
	
	private final PublisherRepository publisherRepository;
	
	private final StoryRepository storyRepository;

    
	public Publisher getPublisherById(UUID publisherId) throws ServiceException {
        return publisherRepository.findById(publisherId)
            .orElseThrow(() -> new ServiceException("Publisher not found"));
    }

    @Transactional
    public Story addStory(UUID publisherId, StoryRequest storyRequest) throws ServiceException {
        Publisher publisher = getPublisherById(publisherId);
        if (publisher == null) {
            throw new ServiceException("Publisher not found");
        }
        
        Story story = new Story();
        story.setTitle(storyRequest.getTitle());
        story.setContent(storyRequest.getContent());
        story.setPublisher(publisher);

        return storyRepository.save(story);
    }

    public List<Comment> getCommentsForPublisher(UUID publisherId) throws ServiceException {
        Publisher publisher = getPublisherById(publisherId);
        return publisher.getStories().stream()
                .flatMap(story -> story.getComments().stream())
                .collect(Collectors.toList());
    }

}

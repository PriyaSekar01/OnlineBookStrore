package com.onlinebookstore.service;

import java.util.UUID;

import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;

import com.onlinebookstore.entity.Publisher;
import com.onlinebookstore.repository.PublisherRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PublisherService {
	
	private final PublisherRepository publisherRepository;

    
    public Publisher getPublisherById(UUID id) {
        return publisherRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Publisher not found with id: " + id));
    }

}

package com.onlinebookstore.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onlinebookstore.entity.Story;

@Repository
public interface StoryRepository extends JpaRepository<Story, UUID> {

    List<Story> findByIsPublishedTrue();

    void deleteById(UUID id); // Change the parameter type to UUID

    Optional<Story> findById(UUID id); // Change the parameter and return type to UUID
}

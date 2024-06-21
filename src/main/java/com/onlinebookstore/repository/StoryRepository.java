package com.onlinebookstore.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onlinebookstore.entity.Story;


@Repository
public interface StoryRepository extends JpaRepository<Story,UUID>{

}

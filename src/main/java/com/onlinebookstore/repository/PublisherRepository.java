package com.onlinebookstore.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onlinebookstore.entity.Publisher;


@Repository
public interface PublisherRepository extends JpaRepository<Publisher, UUID>{

}

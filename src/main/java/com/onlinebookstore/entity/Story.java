package com.onlinebookstore.entity;

import java.util.ArrayList;
import java.util.List;

import com.onlinebookstore.baseentities.AuditingWithBaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "stories")
public class Story extends AuditingWithBaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String genre;

    @Column(nullable = false)
    @Lob
    private String content;

    @ElementCollection
    private List<String> comments = new ArrayList<>();

    private int likes = 0;

    private boolean isPublished = false;
}

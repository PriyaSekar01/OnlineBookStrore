package com.onlinebookstore.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.onlinebookstore.baseentities.AuditingWithBaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stories")
public class Story extends AuditingWithBaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "publisher_id", nullable = false)
    private Publisher publisher;

    @OneToMany(mappedBy = "story", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();
    
    // Constructors, getters, setters, and other methods as needed
}


package com.onlinebookstore.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.onlinebookstore.baseentities.AuditingWithBaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "publishers")
public class Publisher extends AuditingWithBaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL)
    private List<Story> stories = new ArrayList<>();

    @OneToMany(mappedBy = "publisher")
    private List<Comment> comments = new ArrayList<>();
    
    // Constructors, getters, setters, and other methods as needed
}


package com.onlinebookstore.entity;

import java.io.Serializable;

import com.onlinebookstore.baseentities.AuditingWithBaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "comments")
public class Comment extends AuditingWithBaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "text")
	private String text;

	@ManyToOne
	@JoinColumn(name = "story_id")
	private Story story;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

}

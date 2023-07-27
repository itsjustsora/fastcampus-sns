package com.fastcampus.sns.model;

import java.sql.Timestamp;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.fastcampus.sns.model.entity.PostEntity;
import com.fastcampus.sns.model.entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Post {

	private Integer id;

	private String title;

	private String body;

	private User user;

	private Timestamp registeredAt;

	private Timestamp updatedAt;

	private Timestamp deletedAt;

	public static Post fromEntity(PostEntity entity) {
		return new Post(
			entity.getId(),
			entity.getTitle(),
			entity.getBody(),
			User.fromEntity(entity.getUser()),
			entity.getRegisteredAt(),
			entity.getUpdatedAt(),
			entity.getDeletedAt()
		);
	}
}

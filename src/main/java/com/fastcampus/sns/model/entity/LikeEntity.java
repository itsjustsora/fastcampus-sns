package com.fastcampus.sns.model.entity;

import java.sql.Timestamp;
import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "\"like\"", indexes = {
	@Index(name = "like_post_id_idx", columnList = "post_id"),
	@Index(name = "like_user_id_idx", columnList = "user_id")
})
@SQLDelete(sql = "UPDATE \"like\" SET deleted_at = NOW() WHERE id=?")
@Where(clause = "deleted_at is NULL")
@NoArgsConstructor
public class LikeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserEntity user;

	@ManyToOne
	@JoinColumn(name = "post_id")
	private PostEntity post;

	private Timestamp registeredAt;

	private Timestamp updatedAt;

	private Timestamp deletedAt;

	@PrePersist
	void registeredAt() {
		this.registeredAt = Timestamp.from(Instant.now());
	}

	@PreUpdate
	void updatedAt() {
		this.updatedAt = Timestamp.from(Instant.now());
	}

	public static LikeEntity of(UserEntity userEntity, PostEntity postEntity) {
		LikeEntity entity = new LikeEntity();
		entity.setUser(userEntity);
		entity.setPost(postEntity);
		return entity;
	}
}

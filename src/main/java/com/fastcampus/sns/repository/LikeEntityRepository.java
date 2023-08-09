package com.fastcampus.sns.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fastcampus.sns.model.entity.LikeEntity;
import com.fastcampus.sns.model.entity.PostEntity;
import com.fastcampus.sns.model.entity.UserEntity;

@Repository
public interface LikeEntityRepository extends JpaRepository<LikeEntity, Integer> {
	Optional<LikeEntity> findByUserAndPost(UserEntity user, PostEntity post);

	@Query(value = "SELECT COUNT(*) FROM LikeEntity entity WHERE entity.post = :post")
	Integer countByPost(@Param("post") PostEntity post);

	List<LikeEntity> findAllByPost(PostEntity post);
}

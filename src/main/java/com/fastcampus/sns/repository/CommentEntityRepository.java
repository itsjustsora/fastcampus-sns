package com.fastcampus.sns.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fastcampus.sns.model.entity.CommentEntity;
import com.fastcampus.sns.model.entity.PostEntity;

@Repository
public interface CommentEntityRepository extends JpaRepository<CommentEntity, Integer> {
	Page<CommentEntity> findAllByPost(PostEntity post, Pageable pageable);
}

package com.fastcampus.sns.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fastcampus.sns.model.entity.PostEntity;

@Repository
public interface PostEntityRepository extends JpaRepository<PostEntity, Integer> {
	public Page<PostEntity> findAllByUserId(Integer userId, Pageable pageable);
}

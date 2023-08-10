package com.fastcampus.sns.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fastcampus.sns.model.entity.AlarmEntity;

@Repository
public interface AlarmEntityRepository extends JpaRepository<AlarmEntity, Integer> {
	Page<AlarmEntity> findAllByUserId(Integer userId, Pageable pageable);
}

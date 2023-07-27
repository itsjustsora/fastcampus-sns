package com.fastcampus.sns.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fastcampus.sns.exception.ErrorCode;
import com.fastcampus.sns.exception.SnsApplicationException;
import com.fastcampus.sns.model.Post;
import com.fastcampus.sns.model.entity.PostEntity;
import com.fastcampus.sns.model.entity.UserEntity;
import com.fastcampus.sns.repository.PostEntityRepository;
import com.fastcampus.sns.repository.UserEntityRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

	private final PostEntityRepository postEntityRepository;
	private final UserEntityRepository userEntityRepository;


	@Transactional
	public void create(String title, String body, String username) {
		UserEntity userEntity = userEntityRepository.findByUsername(username).orElseThrow(
			() -> new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", username)));

		postEntityRepository.save(PostEntity.of(title, body, userEntity));
	}

	@Transactional
	public Post modify(String title, String body, String username, Integer postId) {
		UserEntity userEntity = userEntityRepository.findByUsername(username).orElseThrow(
			() -> new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", username)));

		PostEntity postEntity = postEntityRepository.findById(postId).orElseThrow(
			() -> new SnsApplicationException(ErrorCode.POST_NOT_FOUND, String.format("%s not founded", postId))
		);

		// post permisson
		if (postEntity.getUser() != userEntity) {
			throw new SnsApplicationException(ErrorCode.INVALID_PERMISSION,
				String.format("%s has no permission with %s", username, postId));
		}

		postEntity.setTitle(title);
		postEntity.setBody(body);

		return Post.fromEntity(postEntityRepository.saveAndFlush(postEntity));
	}
}

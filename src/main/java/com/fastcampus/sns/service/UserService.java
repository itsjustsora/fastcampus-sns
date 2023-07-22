package com.fastcampus.sns.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fastcampus.sns.exception.ErrorCode;
import com.fastcampus.sns.exception.SnsApplicationException;
import com.fastcampus.sns.model.User;
import com.fastcampus.sns.model.entity.UserEntity;
import com.fastcampus.sns.repositor.UserEntityRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private UserEntityRepository userEntityRepository;

	public User join(String username, String password) {
		// 회원가입하려는 username으로 회원가입된 유저가 있는지
		userEntityRepository.findByUsername(username).ifPresent(it -> {
			throw new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME, String.format("%s is duplicated.", username));
		});

		// 회원가입 진행 = user 등록
		UserEntity userEntity = userEntityRepository.save(UserEntity.of(username, password));

		return User.fromEntity(userEntity);
	}

	// TODO : implement
	public String login(String username, String password) {
		// 회원가입 여부 체크
		UserEntity userEntity = userEntityRepository.findByUsername(username).orElseThrow(
			() -> new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME, ""));

		// 비밀번호 체크
		if (!userEntity.getPassword().equals(password)) {
			throw new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME, "");
		}

		// 토큰 생성

		return "";
	}
}

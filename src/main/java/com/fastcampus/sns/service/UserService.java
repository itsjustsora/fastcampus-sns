package com.fastcampus.sns.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fastcampus.sns.exception.ErrorCode;
import com.fastcampus.sns.exception.SnsApplicationException;
import com.fastcampus.sns.model.User;
import com.fastcampus.sns.model.entity.UserEntity;
import com.fastcampus.sns.repository.UserEntityRepository;
import com.fastcampus.sns.util.JwtTokenUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserEntityRepository userEntityRepository;
	private final BCryptPasswordEncoder encoder;

	@Value("${jwt.secret-key}")
	private String secretKey;

	@Value("${jwt.token.expired-time-ms}")
	private Long expiredTimeMs;

	public User loadUserByUsername(String username) {
		return userEntityRepository.findByUsername(username).map(User::fromEntity).orElseThrow(
			() -> new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", username))
		);
	}

	@Transactional
	public User join(String username, String password) {
		// 회원가입하려는 username으로 회원가입된 유저가 있는지
		userEntityRepository.findByUsername(username).ifPresent(it -> {
			throw new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME, String.format("%s is duplicated.", username));
		});

		// 회원가입 진행 = user 등록
		UserEntity userEntity = userEntityRepository.save(UserEntity.of(username, encoder.encode(password)));

		return User.fromEntity(userEntity);
	}

	// TODO : implement
	public String login(String username, String password) {
		// 회원가입 여부 체크
		UserEntity userEntity = userEntityRepository.findByUsername(username).orElseThrow(
			() -> new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", username)));

		// 비밀번호 체크
		if(!encoder.matches(password, userEntity.getPassword())) {
			throw new SnsApplicationException(ErrorCode.INVALID_PASSWORD);
		}

		// 토큰 생성x
		String token = JwtTokenUtils.generateToken(username, secretKey, expiredTimeMs);

		return token;
	}
}
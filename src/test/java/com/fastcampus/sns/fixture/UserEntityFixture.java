package com.fastcampus.sns.fixture;

import com.fastcampus.sns.model.entity.UserEntity;

public class UserEntityFixture {
	// 테스트용 UserEntity
	public static UserEntity get(String username, String password, Integer userId) {
		UserEntity result = new UserEntity();
		result.setId(userId);
		result.setUsername(username);
		result.setPassword(password);
		return result;
	}
}

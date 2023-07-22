package com.fastcampus.sns.controller.response;

import com.fastcampus.sns.model.User;
import com.fastcampus.sns.model.UserRole;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserJoinResponse {
	private Integer id;
	private String username;
	private UserRole role;

	public static UserJoinResponse fromUser(User user) {
		return new UserJoinResponse(
			user.getId(),
			user.getUsername(),
			user.getUserRole()
		);
	}
}

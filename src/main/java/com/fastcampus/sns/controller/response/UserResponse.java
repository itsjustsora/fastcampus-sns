package com.fastcampus.sns.controller.response;

import java.sql.Timestamp;

import com.fastcampus.sns.model.User;
import com.fastcampus.sns.model.UserRole;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponse {

	private Integer id;
	private String username;
	private UserRole role;
	private Timestamp registeredAt;
	private Timestamp updatedAt;
	private Timestamp deletedAt;

	public static UserResponse fromUser(User user) {
		return new UserResponse(
			user.getId(),
			user.getUsername(),
			user.getRole(),
			user.getRegisteredAt(),
			user.getUpdatedAt(),
			user.getDeletedAt()
		);
	}
}

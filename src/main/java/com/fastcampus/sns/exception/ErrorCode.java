package com.fastcampus.sns.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	DUPLICATED_USER_NAME(HttpStatus.CONFLICT, "username is duplicated."),;

	private HttpStatus status;
	private String message;
}

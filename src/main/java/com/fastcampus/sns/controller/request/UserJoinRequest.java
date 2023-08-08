package com.fastcampus.sns.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserJoinRequest {

	@JsonProperty
	private String name;

	@JsonProperty
	private String password;
}

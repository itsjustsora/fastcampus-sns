package com.fastcampus.sns.fixture;

import lombok.Data;

public class TestInfoFixture {

	@Data
	public static class TestInfo {
		private Integer postId;
		private Integer userId;
		private String username;
		private String password;
		private String title;
		private String body;
	}

	public static TestInfo get() {
		TestInfo info = new TestInfo();
		info.setPostId(1);
		info.setUserId(1);
		info.setUsername("name");
		info.setPassword("password");
		info.setTitle("title");
		info.setBody("body");
		return info;
	}
}

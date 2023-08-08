package com.fastcampus.sns.service;

import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fastcampus.sns.exception.ErrorCode;
import com.fastcampus.sns.exception.SnsApplicationException;
import com.fastcampus.sns.fixture.PostEntityFixture;
import com.fastcampus.sns.fixture.UserEntityFixture;
import com.fastcampus.sns.model.entity.LikeEntity;
import com.fastcampus.sns.model.entity.PostEntity;
import com.fastcampus.sns.model.entity.UserEntity;
import com.fastcampus.sns.repository.LikeEntityRepository;
import com.fastcampus.sns.repository.PostEntityRepository;
import com.fastcampus.sns.repository.UserEntityRepository;

@SpringBootTest
class PostServiceTest {

	@Autowired
	private PostService postService;

	@MockBean
	private PostEntityRepository postEntityRepository;

	@MockBean
	private UserEntityRepository userEntityRepository;

	@MockBean
	private LikeEntityRepository likeEntityRepository;

	@Test
	void 포스트작성이_성공한경우() {
		String title = "title";
		String body = "body";
		String username = "username";

		when(userEntityRepository.findByUserName(username)).thenReturn(Optional.of(mock(UserEntity.class)));
		when(postEntityRepository.save(any())).thenReturn(mock(PostEntity.class));

		Assertions.assertDoesNotThrow(() -> postService.create(title, body, username));
	}

	@Test
	void 포스트작성시_요청한유저가_존재하지않는경우() {
		String title = "title";
		String body = "body";
		String username = "username";

		when(userEntityRepository.findByUserName(username)).thenReturn(Optional.empty());

		SnsApplicationException e = Assertions.assertThrows(SnsApplicationException.class, () -> postService.create(title, body, username));
		Assertions.assertEquals(ErrorCode.USER_NOT_FOUND, e.getErrorCode());
	}

	@Test
	void 포스트수정이_성공한경우() {
		String title = "title";
		String body = "body";
		String username = "username";
		Integer postId = 1;

		PostEntity postEntity = PostEntityFixture.get(username, postId, 1);
		UserEntity userEntity = postEntity.getUser();

		when(userEntityRepository.findByUserName(username)).thenReturn(Optional.of(userEntity));
		when(postEntityRepository.findById(postId)).thenReturn(Optional.of(postEntity));
		when(postEntityRepository.saveAndFlush(any())).thenReturn(postEntity);

		Assertions.assertDoesNotThrow(() -> postService.modify(title, body, username, postId));
	}

	@Test
	void 포스트수정시_포스트가_존재하지_않는_경우() {
		String title = "title";
		String body = "body";
		String username = "username";
		Integer postId = 1;

		PostEntity postEntity = PostEntityFixture.get(username, postId, 1);
		UserEntity userEntity = postEntity.getUser();

		when(userEntityRepository.findByUserName(username)).thenReturn(Optional.of(userEntity));
		when(postEntityRepository.findById(postId)).thenReturn(Optional.empty());

		SnsApplicationException e = Assertions.assertThrows(SnsApplicationException.class,
			() -> postService.modify(title, body, username, postId));
		Assertions.assertEquals(ErrorCode.POST_NOT_FOUND, e.getErrorCode());
	}

	@Test
	void 포스트수정시_권한이_없는_경우() {
		String title = "title";
		String body = "body";
		String username = "username";
		Integer postId = 1;

		PostEntity postEntity = PostEntityFixture.get(username, postId, 1);
		UserEntity writer = UserEntityFixture.get("username1", "password", 2);

		when(userEntityRepository.findByUserName(username)).thenReturn(Optional.of(writer));
		when(postEntityRepository.findById(postId)).thenReturn(Optional.of(postEntity));

		SnsApplicationException e = Assertions.assertThrows(SnsApplicationException.class,
			() -> postService.modify(title, body, username, postId));
		Assertions.assertEquals(ErrorCode.INVALID_PERMISSION, e.getErrorCode());
	}

	@Test
	void 포스트삭제가_성공한경우() {
		String username = "username";
		Integer postId = 1;

		PostEntity postEntity = PostEntityFixture.get(username, postId, 1);
		UserEntity userEntity = postEntity.getUser();

		when(userEntityRepository.findByUserName(username)).thenReturn(Optional.of(userEntity));
		when(postEntityRepository.findById(postId)).thenReturn(Optional.of(postEntity));

		Assertions.assertDoesNotThrow(() -> postService.delete(username, postId));
	}

	@Test
	void 포스트삭제시_포스트가_존재하지_않는_경우() {
		String username = "username";
		Integer postId = 1;

		PostEntity postEntity = PostEntityFixture.get(username, postId, 1);
		UserEntity userEntity = postEntity.getUser();

		when(userEntityRepository.findByUserName(username)).thenReturn(Optional.of(userEntity));
		when(postEntityRepository.findById(postId)).thenReturn(Optional.empty());

		SnsApplicationException e = Assertions.assertThrows(SnsApplicationException.class,
			() -> postService.delete(username, postId));
		Assertions.assertEquals(ErrorCode.POST_NOT_FOUND, e.getErrorCode());
	}

	@Test
	void 포스트삭제시_권한이_없는_경우() {
		String username = "username";
		Integer postId = 1;

		PostEntity postEntity = PostEntityFixture.get(username, postId, 1);
		UserEntity writer = UserEntityFixture.get("username1", "password", 2);

		when(userEntityRepository.findByUserName(username)).thenReturn(Optional.of(writer));
		when(postEntityRepository.findById(postId)).thenReturn(Optional.of(postEntity));

		SnsApplicationException e = Assertions.assertThrows(SnsApplicationException.class,
			() -> postService.delete(username, postId));
		Assertions.assertEquals(ErrorCode.INVALID_PERMISSION, e.getErrorCode());
	}

	@Test
	void 피드목록요청이_성공한경우() {
		Pageable pageable = mock(Pageable.class);
		when(postEntityRepository.findAll(pageable)).thenReturn(Page.empty());
		Assertions.assertDoesNotThrow(() -> postService.list(pageable));
	}

	@Test
	void 내피드목록요청이_성공한경우() {
		Pageable pageable = mock(Pageable.class);
		UserEntity user = mock(UserEntity.class);
		when(userEntityRepository.findByUserName(any())).thenReturn(Optional.of(user));
		when(postEntityRepository.findAllByUser(user, pageable)).thenReturn(Page.empty());
		Assertions.assertDoesNotThrow(() -> postService.my("", pageable));
	}

	@Test
	void 좋아요요청이_성공한경우() {
		String username = "username";
		Integer postId = 1;

		PostEntity postEntity = PostEntityFixture.get(username, postId, 1);
		UserEntity userEntity = postEntity.getUser();

		when(userEntityRepository.findByUserName(username)).thenReturn(Optional.of(userEntity));
		when(postEntityRepository.findById(any())).thenReturn(Optional.of(postEntity));
		when(likeEntityRepository.findByUserAndPost(userEntity, postEntity)).thenReturn(Optional.empty());

		Assertions.assertDoesNotThrow(() -> postService.like(postId, username));
	}

	@Test
	void 좋아요한_게시물이_이미_있는경우() {
		String username = "username";
		Integer postId = 1;

		PostEntity postEntity = PostEntityFixture.get(username, postId, 1);
		UserEntity userEntity = postEntity.getUser();

		when(userEntityRepository.findByUserName(username)).thenReturn(Optional.of(userEntity));
		when(postEntityRepository.findById(any())).thenReturn(Optional.of(postEntity));
		when(likeEntityRepository.findByUserAndPost(userEntity, postEntity)).thenReturn(
			Optional.of(mock(LikeEntity.class)));

		SnsApplicationException e = Assertions.assertThrows(SnsApplicationException.class,
			() -> postService.like(postId, username));
		Assertions.assertEquals(ErrorCode.ALREADY_LIKED, e.getErrorCode());
	}

	@Test
	void 좋아요수_조회가_성공한경우() {
		Integer postId = 1;

		when(postEntityRepository.findById(any())).thenReturn(Optional.of(mock(PostEntity.class)));

		Assertions.assertDoesNotThrow(() -> postService.likeCount(postId));
	}

	@Test
	void 좋아요수_조회시_해당_포스트가_없는경우() {
		Integer postId = 1;

		when(postEntityRepository.findById(any())).thenReturn(Optional.empty());

		SnsApplicationException e = Assertions.assertThrows(SnsApplicationException.class,
			() -> postService.likeCount(postId));
		Assertions.assertEquals(ErrorCode.POST_NOT_FOUND, e.getErrorCode());
	}

}

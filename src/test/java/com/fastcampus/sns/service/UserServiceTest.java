package com.fastcampus.sns.service;

import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.fastcampus.sns.exception.SnsApplicationException;
import com.fastcampus.sns.fixture.UserEntityFixture;
import com.fastcampus.sns.model.entity.UserEntity;
import com.fastcampus.sns.repositor.UserEntityRepository;

@SpringBootTest
class UserServiceTest {

	@Autowired
	private UserService userService;

	@MockBean
	private UserEntityRepository userEntityRepository;

	@Test
	void 회원가입이_정상적으로_동작하는_경우() {
		String username = "username";
		String password = "password";

		when(userEntityRepository.findByUsername(username)).thenReturn(Optional.empty());
		when(userEntityRepository.save(any())).thenReturn(Optional.of(UserEntityFixture.get(username, password)));

		Assertions.assertDoesNotThrow(() ->  userService.join(username, password));
	}

	@Test
	void 회원가입시_username이_중복되는_경우_에러반환() {
		String username = "username";
		String password = "password";

		UserEntity fixture = UserEntityFixture.get(username, password);

		when(userEntityRepository.findByUsername(username)).thenReturn(Optional.of(fixture));
		when(userEntityRepository.save(any())).thenReturn(Optional.of(fixture));

		Assertions.assertThrows(SnsApplicationException.class, () ->  userService.join(username, password));
	}

	@Test
	void 로그인이_정상적으로_동작하는_경우() {
		String username = "username";
		String password = "password";

		UserEntity fixture = UserEntityFixture.get(username, password);

		when(userEntityRepository.findByUsername(username)).thenReturn(Optional.of(fixture));
		Assertions.assertDoesNotThrow(() ->  userService.login(username, password));
	}

	@Test
	void 로그인시_username으로_회원가입한_유저가_없는_경우_에러반환() {
		String username = "username";
		String password = "password";

		when(userEntityRepository.findByUsername(username)).thenReturn(Optional.empty());

		Assertions.assertThrows(SnsApplicationException.class, () ->  userService.login(username, password));
	}

	@Test
	void 로그인시_패스워드가_일치하지_않는_경우_에러반환() {
		String username = "username";
		String password = "password";
		String wrongPassword = "wrong";

		UserEntity fixture = UserEntityFixture.get(username, password);
		when(userEntityRepository.findByUsername(username)).thenReturn(Optional.of(fixture));


		Assertions.assertThrows(SnsApplicationException.class, () ->  userService.login(username, wrongPassword));
	}
}
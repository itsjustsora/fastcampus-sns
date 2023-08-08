package com.fastcampus.sns.model;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fastcampus.sns.model.entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class User implements UserDetails {
	private Integer id;
	private String username;
	private String password;
	private UserRole role;
	private Timestamp registeredAt;
	private Timestamp updatedAt;
	private Timestamp deletedAt;

	public static User fromEntity(UserEntity entity) {
		return new User(
			entity.getId(),
			entity.getUserName(),
			entity.getPassword(),
			entity.getRole(),
			entity.getRegisteredAt(),
			entity.getUpdatedAt(),
			entity.getDeletedAt()
		);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.toString()));
	}

	@Override
	public boolean isAccountNonExpired() {
		return deletedAt == null;
	}

	@Override
	public boolean isAccountNonLocked() {
		return deletedAt == null;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return deletedAt == null;
	}

	@Override
	public boolean isEnabled() {
		return deletedAt == null;
	}
}

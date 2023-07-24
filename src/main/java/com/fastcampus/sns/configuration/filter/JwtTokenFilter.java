package com.fastcampus.sns.configuration.filter;

import java.io.IOException;
import java.util.List;
import java.util.logging.Filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fastcampus.sns.model.User;
import com.fastcampus.sns.service.UserService;
import com.fastcampus.sns.util.JwtTokenUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

	private final String key;
	private final UserService userService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		// get Header
		final String header = request.getHeader(HttpHeaders.AUTHORIZATION);

		if(header == null || !header.startsWith("Bearer ")) {
			log.error("Error occurs while getting header, header is null or invalid.");
			filterChain.doFilter(request, response);
			return;
		}

		try {
			final String token = header.split(" ")[1].trim();

			if (JwtTokenUtils.isExpired(token, key)) {
				log.error("key is expired");
				filterChain.doFilter(request, response);
				return;
			}
			String username = JwtTokenUtils.getUsername(token, key);
			User user = userService.loadUserByUsername(username);

			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
				user, null, user.getAuthorities()
			);

			authentication.setDetails(new WebAuthenticationDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authentication);

		} catch (RuntimeException e) {
			log.error("Error occurs while validating, {}", e.toString());
			filterChain.doFilter(request, response);
			return;
		}
		filterChain.doFilter(request, response);
	}
}

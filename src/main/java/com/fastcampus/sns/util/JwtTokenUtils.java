package com.fastcampus.sns.util;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtTokenUtils {

	public static String getUsername(String token, String key) {
		return extractClaim(token, key).get("username", String.class);
	}

	public static boolean isExpired(String token, String key) {
		Date expiration = extractClaim(token, key).getExpiration();
		return expiration.before(new Date());
	}

	private static Claims extractClaim(String token, String key) {
		return Jwts.parserBuilder()
			.setSigningKey(getSigningKey(key))
			.build()
			.parseClaimsJws(token)
			.getBody();
	}

	public static String generateToken(String username, String key, long expiredTimeMs) {
		Claims claims = Jwts.claims();
		claims.put("username", username);

		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + expiredTimeMs))
			.signWith(getSigningKey(key), SignatureAlgorithm.HS256)
			.compact();
	}

	private static Key getSigningKey(String secretKey) {
		byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}

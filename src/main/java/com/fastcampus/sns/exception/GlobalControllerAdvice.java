package com.fastcampus.sns.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fastcampus.sns.controller.response.Response;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

	@ExceptionHandler(SnsApplicationException.class)
	public ResponseEntity<?> applicationHandler(SnsApplicationException e) {
		log.error("Error code {}", e.toString());
		return ResponseEntity.status(e.getErrorCode().getStatus())
			.body(Response.error(e.getErrorCode().name()));
	}
}

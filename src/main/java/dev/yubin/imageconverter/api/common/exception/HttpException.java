package dev.yubin.imageconverter.api.common.exception;

import org.springframework.http.HttpStatus;

public abstract class HttpException extends RuntimeException {
	private final HttpStatus status;

	protected HttpException(String message, HttpStatus status) {
		super(message);
		this.status = status;
	}

	public HttpStatus getStatus() {
		return status;
	}
}

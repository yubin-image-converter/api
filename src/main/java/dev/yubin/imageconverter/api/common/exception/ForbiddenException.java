package dev.yubin.imageconverter.api.common.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends HttpException {
  public ForbiddenException(String message) {
    super(message, HttpStatus.FORBIDDEN);
  }
}

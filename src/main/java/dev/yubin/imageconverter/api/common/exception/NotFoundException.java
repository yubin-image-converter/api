package dev.yubin.imageconverter.api.common.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends HttpException {
  public NotFoundException(String message) {
    super(message, HttpStatus.NOT_FOUND);
  }
}

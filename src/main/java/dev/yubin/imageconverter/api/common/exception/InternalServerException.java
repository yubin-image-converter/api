package dev.yubin.imageconverter.api.common.exception;

import org.springframework.http.HttpStatus;

public class InternalServerException extends HttpException {
  public InternalServerException(String message) {
    super(message, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}

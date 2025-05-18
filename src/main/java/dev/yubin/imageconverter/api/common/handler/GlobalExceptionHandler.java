package dev.yubin.imageconverter.api.common.handler;

import dev.yubin.imageconverter.api.common.dto.ErrorResponse;
import dev.yubin.imageconverter.api.common.exception.HttpException;
import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(HttpException.class)
  public ResponseEntity<ErrorResponse> handleCustomException(HttpException ex) {
    log.warn("Handled custom exception: {}", ex.getMessage());

    return ResponseEntity.status(ex.getStatus())
        .body(
            ErrorResponse.builder()
                .status(ex.getStatus().value())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build());
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(
            ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build());
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<ErrorResponse> handleMethodNotSupported(
      HttpRequestMethodNotSupportedException ex) {
    return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
        .body(
            ErrorResponse.builder()
                .status(HttpStatus.METHOD_NOT_ALLOWED.value())
                .message("지원하지 않는 HTTP 메서드입니다")
                .timestamp(LocalDateTime.now())
                .build());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleUnhandledException(Exception ex) {
    log.error("Unhandled exception", ex);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(
            ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("서버 오류가 발생했습니다.")
                .timestamp(LocalDateTime.now())
                .build());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationException(
      MethodArgumentNotValidException ex) {
    String message =
        ex.getBindingResult().getFieldErrors().stream()
            .map(error -> error.getField() + ": " + error.getDefaultMessage())
            .findFirst()
            .orElse("잘못된 요청입니다.");

    ErrorResponse error =
        ErrorResponse.builder()
            .status(HttpStatus.BAD_REQUEST.value())
            .message(message)
            .timestamp(LocalDateTime.now())
            .build();

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }
}

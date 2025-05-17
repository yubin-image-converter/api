package dev.yubin.imageconverter.api.common.handler;

import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handleValidationError(MethodArgumentNotValidException ex) {
    String msg = ex.getBindingResult().getFieldError().getDefaultMessage();
    return ResponseEntity.badRequest().body(Map.of("error", msg));
  }
}

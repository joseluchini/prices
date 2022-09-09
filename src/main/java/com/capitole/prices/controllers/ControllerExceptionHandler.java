package com.capitole.prices.controllers;

import com.capitole.prices.errors.ApiException;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ControllerExceptionHandler {

  private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);

  @ExceptionHandler(ApiException.class)
  protected ResponseEntity<Object> handleApiException(ApiException ae) {
    return ae.buildRestResponse();
  }

  @ExceptionHandler(Exception.class)
  protected ResponseEntity<Object> handleException(Exception e) {
    LOG.error("Unexpected error {1}", e);
    return new ResponseEntity<>(Map.of("description", "Unexpected API error"), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}

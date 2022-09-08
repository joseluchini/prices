package com.capitole.prices.errors;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CustomErrors {

  PRICE_NOT_FOUND("Requested price has not been found", HttpStatus.NOT_FOUND.value()),
  UNEXPECTED_SERVICE_ERROR("Unexpected error processing request", HttpStatus.INTERNAL_SERVER_ERROR.value()),
  UNEXPECTED_REPO_ERROR("Unexpected error accessing into repository", HttpStatus.INTERNAL_SERVER_ERROR.value());

  private String description;
  private int status;

  CustomErrors(String description, int status) {
    this.description = description;
    this.status = status;
  }
}

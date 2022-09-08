package com.capitole.prices.errors;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString
public class ApiException extends Exception {

  private CustomErrors customError;
  private Exception exception;

  public ApiException(CustomErrors customError, Exception exception) {
    this.customError = customError;
    this.exception = exception;
  }

  public ResponseEntity<Object> buildRestResponse() {
    Map<String, Object> map = new HashMap<>();
    map.put("description", this.customError.getDescription());

    return new ResponseEntity<>(map, HttpStatus.valueOf(this.customError.getStatus()));
  }
}

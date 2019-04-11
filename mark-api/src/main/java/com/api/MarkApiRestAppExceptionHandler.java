package com.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.api.dto.Response;
import com.api.exception.MarkException;

/**
 * @author Rohit Raj
 *
 */

@ControllerAdvice
public class MarkApiRestAppExceptionHandler {

  /**
   * Handle Mark exception.
   *
   * @param e the e
   * @return the response entity
   */
	
  @ExceptionHandler(MarkException.class)
  public ResponseEntity<Response<String>> handleMarkException(Exception e) {
    return new ResponseEntity<Response<String>>(new Response<String>(
        HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * Handle illegal argument exception.
   *
   * @param e the exception
   * @return the response entity
   */
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Response<String>> handleIllegalArgumentException(Exception e) {
    return new ResponseEntity<Response<String>>(new Response<String>(
        HttpStatus.BAD_REQUEST.value(), e.getMessage(), null), HttpStatus.BAD_REQUEST);
  }
  
}

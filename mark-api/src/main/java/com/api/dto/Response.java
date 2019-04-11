package com.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author RITESH SINGH
 * @since JDK 1.8
 * @version 1.0
 *
 */
@JsonInclude(value = Include.NON_EMPTY)
public class Response<T> {

  private int status;
  private String message;
  private T data;
  private Integer count;

  public Response(int status, String message, T data, Integer count) {
    super();
    this.status = status;
    this.message = message;
    this.data = data;
    this.count = count;
  }

  public Response(int status, String message, T data) {
    super();
    this.status = status;
    this.message = message;
    this.data = data;
  }

  public Response() {
    super();
  }

  public int getStatus() {
    return status;
  }

  public String getMessage() {
    return message;
  }

  public T getData() {
    return data;
  }

  public Integer getCount() {
    return count;
  }

}

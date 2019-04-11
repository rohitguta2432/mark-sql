package com.api.exception;

/**
 * @author RITESH SINGH
 * @since JDK 1.8
 * @version 1.0
 *
 */
public class AuthenticationTokenException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  /**
 * <b>Constructor</b>
 */
public AuthenticationTokenException() {}

  /**
   * <b>Constructor</b>
 * @param message as String
 */
public AuthenticationTokenException(String message) {
    super(message);
  }

  /**
   * <b>Constructor</b>
 * @param cause as Throwable
 */
public AuthenticationTokenException(Throwable cause) {
    super(cause);
  }

  /**
   * <b>Constructor</b>
 * @param message as String
 * @param cause as Throwable
 */
public AuthenticationTokenException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * <b>Constructor</b>
 * @param message as String
 * @param cause as Throwable
 * @param enableSuppression as boolean
 * @param writableStackTrace as boolean
 */
public AuthenticationTokenException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}

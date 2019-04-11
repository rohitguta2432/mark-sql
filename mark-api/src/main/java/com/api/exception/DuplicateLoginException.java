package com.api.exception;

/**
 * @author RITESH SINGH
 * @since JDK 1.8
 * @version 1.0
 *
 */
public class DuplicateLoginException extends RuntimeException {

  private static final long serialVersionUID = 1445163827367411539L;

  /** 
   * <b>Constructor</b>
 * @param message as String
 */
public DuplicateLoginException(String message) {
    super(message);
  }

}

package com.api.exception;

/**
 * @author RITESH SINGH
 * @since JDK 1.8
 * @version 1.0
 *
 */
public class UnAuthorizedException extends RuntimeException {

	private static final long serialVersionUID = 3342386728191694632L;

	public UnAuthorizedException() {
	}

	public UnAuthorizedException(String message) {
		super(message);
	}

	public UnAuthorizedException(Throwable cause) {
		super(cause);
	}

	public UnAuthorizedException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnAuthorizedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

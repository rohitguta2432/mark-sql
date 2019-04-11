package com.api.exception;

/**
 * @author RITESH SINGH
 * @since JDK 1.8
 * @version 1.0
 *
 */
public class ExpiredAuthenticationTokenException extends RuntimeException {

	private static final long serialVersionUID = -2183772193871218284L;

	public ExpiredAuthenticationTokenException() {
	}

	public ExpiredAuthenticationTokenException(String message) {
		super(message);
	}

	public ExpiredAuthenticationTokenException(Throwable cause) {
		super(cause);
	}

	public ExpiredAuthenticationTokenException(String message, Throwable cause) {
		super(message, cause);
	}

	public ExpiredAuthenticationTokenException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

package com.api.exception;

/**
 * @author RITESH SINGH
 * @since JDK 1.8
 * @version 1.0
 *
 */
public class MarkException extends RuntimeException {

	private static final long serialVersionUID = 2771174581631905388L;

	public MarkException() {
	}

	public MarkException(String message) {
		super(message);
	}

	public MarkException(Throwable cause) {
		super(cause);
	}

	public MarkException(String message, Throwable cause) {
		super(message, cause);
	}

	public MarkException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

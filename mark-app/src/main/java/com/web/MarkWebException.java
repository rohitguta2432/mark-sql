package com.web;

/**
 * @author RITESH SINGH
 * @since JDK 1.8
 * @version 1.0
 *
 */
public class MarkWebException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public int code;
	
	public MarkWebException() {
		super();
	}
	public MarkWebException(String message, Throwable cause, boolean enableSuppression,
                               boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	public MarkWebException(String message, Throwable cause) {
		super(message, cause);
	}
	public MarkWebException(String message) {
		super(message);
	}
	public MarkWebException(String message, int code) {
		super(message);
		this.code = code;
	}
	public MarkWebException(Throwable cause) {
		super(cause);
	}
}

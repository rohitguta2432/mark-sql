package com.api.exception;

/**
 * @author RITESH SINGH
 * @since JDK 1.8
 * @version 1.0
 *
 */
public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = -3562067878542193963L;

	private int status;

	/**
	 * 
	 * @param status
	 */
	public ValidationException(int status) {
	}

	/**
	 * 
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 * @param status
	 */
	public ValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace,
			int status) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.status = status;
	}

	/**
	 * 
	 * @param message
	 * @param cause
	 * @param status
	 */
	public ValidationException(String message, Throwable cause, int status) {
		super(message, cause);
		this.status = status;
	}

	/**
	 * 
	 * @param message
	 * @param status
	 */
	public ValidationException(String message, int status) {
		super(message);
		this.status = status;
	}

	/**
	 * 
	 * @return returns int
	 */
	public int getStatus() {
		return status;
	}

}

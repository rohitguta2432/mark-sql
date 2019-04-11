package com.api.exception;

/**
 * <h3>This class manages ResourceNotFoundExecption operations</h3>
 * @author RITESH SINGH
 * @since JDK 1.8
 *
 */
public class ResourceNotFoundExecption extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * <b>Constructor</b>
	 */
	public ResourceNotFoundExecption() {
		super();
	}

	/**
	 * <b>Constructor</b>
	 * @param message as String
	 * @param cause as Throwable
	 * @param enableSuppression as boolean
	 * @param writableStackTrace as boolean
	 */
	public ResourceNotFoundExecption(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * <b>Constructor</b>
	 * @param message as String
	 * @param cause as Throwable
	 */
	public ResourceNotFoundExecption(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * <b>ConstructorThrowable</b>
	 * @param message as String
	 */
	public ResourceNotFoundExecption(String message) {
		super(message);
	}

	/**
	 * <b>Constructor</b>
	 * @param cause as Throwable
	 */
	public ResourceNotFoundExecption(Throwable cause) {
		super(cause);
	}

}

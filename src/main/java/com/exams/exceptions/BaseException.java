package com.exams.exceptions;

/**
 * This class serves as the Base Exception class, Other Exceptions would be a
 * wrapper of this object itself
 * 
 * @author ManmeetFIL
 */
public class BaseException extends Exception {

	private static final long serialVersionUID = 1L;

	String message;

	Throwable throwable;

	public BaseException() {
	}

	public BaseException(String message, Throwable throwable) {
		super(message, throwable);
		this.message = message;
	}

	/**
	 * @return the message
	 */
	@Override
	public String getMessage() {
		return this.message;
	}

	/**
	 * @return the throwable
	 */
	public Throwable getThrowable() {
		return throwable;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @param throwable
	 *            the throwable to set
	 */
	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}

	@Override
	public String toString() {
		return this.message;
	}
}

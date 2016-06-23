package com.exams.exceptions;

public class InvalidRequestException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -865133528704393478L;

	private String msg;

	@Override
	public String getMessage() {
		return msg;
	}

	public InvalidRequestException(String msg) {
		super(msg);
		this.msg = msg;
	}
}

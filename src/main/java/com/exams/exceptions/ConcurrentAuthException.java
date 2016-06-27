package com.exams.exceptions;

import org.springframework.security.core.AuthenticationException;

public class ConcurrentAuthException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7891777129013019789L;

	public ConcurrentAuthException(String msg) {
		super(msg);
	}
}

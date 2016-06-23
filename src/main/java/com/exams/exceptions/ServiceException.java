package com.exams.exceptions;

import java.util.List;

import org.springframework.validation.ObjectError;

import com.exams.enums.STATUS;

/**
 * This class will serve as the Exception class for any kind of Service level
 * exceptions thrown in the Service layer of the application.
 *
 * @author ManmeetFIL
 */
public class ServiceException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8968570861374718731L;

	private STATUS result;

	private List<ObjectError> objectErrors;

	public ServiceException() {
		super("Some Service Exception occurred in the App ", null);
	}

	public ServiceException(STATUS result) {
		this.result = result;
	}

	public ServiceException(STATUS result, Throwable e) {
		this.result = result;
		this.throwable = e;
	}

	public ServiceException(STATUS result, String message, Throwable e) {
		this.result = result;
		this.message = message;
		this.throwable = e;
	}

	public ServiceException(STATUS result, List<ObjectError> errors) {
		super();
		this.result = result;
		this.objectErrors = errors;
	}

	public ServiceException(String message, Throwable throwable) {
		super(message, throwable);
		super.setMessage(message);
	}

	public STATUS getResult() {
		return result;
	}

	public void setResult(STATUS result) {
		this.result = result;
	}

	public List<ObjectError> getObjectErrors() {
		return objectErrors;
	}

	public void setObjectErrors(List<ObjectError> objectErrors) {
		this.objectErrors = objectErrors;
	}

	@Override
	public String toString() {
		return super.getMessage();
	}
}

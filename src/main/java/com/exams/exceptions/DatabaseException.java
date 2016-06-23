package com.exams.exceptions;

/**
 * This class wills serve as an Exception for any kind of Database related
 * exceptions arising from the DAO or JdbcTemplates.
 * 
 * @author ManmeetFIL
 */
public class DatabaseException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -715504600859050599L;

	public DatabaseException() {
		super("Some DB exception occurred ", null);
	}

	public DatabaseException(String message, Throwable throwable) {
		super(message, throwable);
		super.setMessage(message);
	}

	@Override
	public String toString() {
		return super.getMessage();
	}
}

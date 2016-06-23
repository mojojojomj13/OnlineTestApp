/**
 *
 */
package com.exams.enums;

/**
 * This enum will have the various values for the result types which will be
 * returned from the RESt service.
 *
 * @author ManmeetFIL, Prithvish Mukherjee
 */
public enum STATUS {
	SUCCESS("SUCCESS"), INTERNAL_SERVER_ERROR("EMPLOYEE DATA APP NOT RESPONDING"), NOT_AUTHENTICATED(
			"THE USER IN THE REQUEST IS NOT AUTHENTICATED"), BAD_REQUEST(
					"THE REQUEST MADE WAS SYNTACTICALLY INCORRECT"), VALIDATOR_ERROR(
							"THE REQUEST MADE WAS SYNTACTICALLY INCORRECT"), NOT_FOUND(
									"THE REQUESTED RESOURCE/CONTENT WAS NOT FOUND");

	String message;

	private STATUS(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}
}

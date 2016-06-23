package com.exams.vo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.exams.enums.STATUS;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * This class is used for creating a wrapper object for any kind of response
 * from the Controller layer to the caller
 * 
 * @author Prithvish Mukherjee
 *
 */
public class ResponseVOBuilder {

	private OnlineExamResponseVO obj;

	public ResponseVOBuilder() {
		obj = new OnlineExamResponseVO();
	}

	public OnlineExamResponseVO build() {
		return obj;
	}

	public ResponseVOBuilder status() {
		obj.setStatus(STATUS.SUCCESS);
		return this;
	}

	public ResponseVOBuilder status(STATUS status) {
		obj.setStatus(status);
		return this;
	}

	public ResponseVOBuilder message() {
		obj.setMessage("SUCCESS");
		return this;
	}

	public ResponseVOBuilder message(String message) {
		obj.setMessage(message);
		return this;
	}

	public ResponseVOBuilder code() {
		obj.setCode(String.valueOf(HttpStatus.OK.value()));
		return this;
	}

	public ResponseVOBuilder code(int code) {
		obj.setCode(String.valueOf(code));
		return this;
	}

	public ResponseVOBuilder totalCount(long totalCount) {
		obj.setTotalCount(String.valueOf(totalCount));
		return this;
	}

	public ResponseVOBuilder result(List<String> result) {
		obj.setResult(result);
		return this;
	}

	@JsonInclude(Include.NON_NULL)
	public static class OnlineExamResponseVO {

		private String code;

		private String message;

		private STATUS status;

		private String totalCount;

		private List<String> result;

		public List<String> getResult() {
			return result;
		}

		private void setResult(List<String> result) {
			result = null == result ? new ArrayList<String>(0) : result;
			this.result = result;
		}

		public Long getTotalCount() {
			Long l = null != totalCount ? Long.parseLong(totalCount) : null;
			return l;
		}

		private void setTotalCount(String totalCount) {
			this.totalCount = totalCount;
		}

		// constructors
		private OnlineExamResponseVO(String code, String message, STATUS status) {
			this.code = code;
			this.message = message;
			this.status = status;
		}

		private OnlineExamResponseVO() {
		}

		private OnlineExamResponseVO(String code, String message) {
			this.code = code;
			this.message = message;
		}

		private OnlineExamResponseVO(String message) {
			this.message = message;
		}

		private void setCode(String code) {
			this.code = code;
		}

		private void setMessage(String message) {
			this.message = message;
		}

		private void setStatus(STATUS status) {
			this.status = status;
		}

		public Integer getCode() {
			Integer i = null != code ? Integer.parseInt(code) : null;
			return i;
		}

		public String getMessage() {
			return message;
		}

		public STATUS getStatus() {
			return status;
		}
	}
}

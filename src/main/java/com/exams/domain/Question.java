package com.exams.domain;

import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Question {

	@NotBlank(message = "question Text cannot be blank")
	@Length(min = 14, message = "minimum 14 length, If you have image instead, then write Not Applicable")
	private String quesText;

	@NotBlank(message = "option 1 cannot be blank")
	private String opA;

	@NotBlank(message = "option 2 cannot be blank")
	private String opB;

	@NotBlank(message = "option 3 cannot be blank")
	private String opC;

	@NotBlank(message = "option 4 cannot be blank")
	private String opD;

	@NotNull(message = "plase select atleast one option")
	@Size(min = 1, message = "please select atleast one, atmost four options", max = 4)
	private List<String> correctOps;

	@NotBlank(message = "marks cannot be blank")
	@Pattern(message = "marks can be only number", regexp = "[0-9]*")
	private String marks;

	private byte[] quesImg;

	public byte[] getQuesImg() {
		return quesImg;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (correctOps == null ? 0 : correctOps.hashCode());
		result = prime * result + (marks == null ? 0 : marks.hashCode());
		result = prime * result + (opA == null ? 0 : opA.hashCode());
		result = prime * result + (opB == null ? 0 : opB.hashCode());
		result = prime * result + (opC == null ? 0 : opC.hashCode());
		result = prime * result + (opD == null ? 0 : opD.hashCode());
		result = prime * result + Arrays.hashCode(quesImg);
		result = prime * result + (quesText == null ? 0 : quesText.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Question other = (Question) obj;
		if (correctOps == null) {
			if (other.correctOps != null)
				return false;
		} else if (!correctOps.equals(other.correctOps))
			return false;
		if (marks == null) {
			if (other.marks != null)
				return false;
		} else if (!marks.equals(other.marks))
			return false;
		if (opA == null) {
			if (other.opA != null)
				return false;
		} else if (!opA.equals(other.opA))
			return false;
		if (opB == null) {
			if (other.opB != null)
				return false;
		} else if (!opB.equals(other.opB))
			return false;
		if (opC == null) {
			if (other.opC != null)
				return false;
		} else if (!opC.equals(other.opC))
			return false;
		if (opD == null) {
			if (other.opD != null)
				return false;
		} else if (!opD.equals(other.opD))
			return false;
		if (!Arrays.equals(quesImg, other.quesImg))
			return false;
		if (quesText == null) {
			if (other.quesText != null)
				return false;
		} else if (!quesText.equals(other.quesText))
			return false;
		return true;
	}

	public void setQuesImg(byte[] quesImg) {
		this.quesImg = quesImg;
	}

	public List<String> getCorrectOps() {
		return correctOps;
	}

	public void setCorrectOps(List<String> correctOps) {
		this.correctOps = correctOps;
	}

	public String getMarks() {
		return marks;
	}

	public void setMarks(String marks) {
		this.marks = marks;
	}

	public String getQuesText() {
		return quesText;
	}

	public void setQuesText(String quesText) {
		this.quesText = quesText;
	}

	public String getOpA() {
		return opA;
	}

	public void setOpA(String opA) {
		this.opA = opA;
	}

	public String getOpB() {
		return opB;
	}

	public void setOpB(String opB) {
		this.opB = opB;
	}

	public String getOpC() {
		return opC;
	}

	public void setOpC(String opC) {
		this.opC = opC;
	}

	public String getOpD() {
		return opD;
	}

	public void setOpD(String opD) {
		this.opD = opD;
	}
}

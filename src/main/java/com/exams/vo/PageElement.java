package com.exams.vo;

import java.util.LinkedHashMap;
import java.util.List;

public class PageElement {

	private String desc;

	private LinkedHashMap<String, Boolean> ops;

	private List<String> userAns;

	private String inputType;

	private String imgSrc;

	public String getImgSrc() {
		return imgSrc;
	}

	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}

	public String getInputType() {
		return inputType;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	private Boolean isAttempted = false;// default

	public Boolean getIsAttempted() {
		return isAttempted;
	}

	public void setIsAttempted(Boolean isAttempted) {
		this.isAttempted = isAttempted;
	}

	public List<String> getUserAns() {
		return userAns;
	}

	public void setUserAns(List<String> userAns) {
		this.userAns = userAns;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public LinkedHashMap<String, Boolean> getOps() {
		return ops;
	}

	public void setOps(LinkedHashMap<String, Boolean> ops) {
		this.ops = ops;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (desc == null ? 0 : desc.hashCode());
		result = prime * result + (isAttempted == null ? 0 : isAttempted.hashCode());
		result = prime * result + (ops == null ? 0 : ops.hashCode());
		result = prime * result + (userAns == null ? 0 : userAns.hashCode());
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
		PageElement other = (PageElement) obj;
		if (desc == null) {
			if (other.desc != null)
				return false;
		} else if (!desc.equals(other.desc))
			return false;
		if (isAttempted == null) {
			if (other.isAttempted != null)
				return false;
		} else if (!isAttempted.equals(other.isAttempted))
			return false;
		if (ops == null) {
			if (other.ops != null)
				return false;
		} else if (!ops.equals(other.ops))
			return false;
		if (userAns == null) {
			if (other.userAns != null)
				return false;
		} else if (!userAns.equals(other.userAns))
			return false;
		return true;
	}
}

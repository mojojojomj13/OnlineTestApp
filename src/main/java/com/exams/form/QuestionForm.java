package com.exams.form;

import java.util.LinkedHashMap;
import java.util.Map;

import com.exams.vo.PageElement;

public class QuestionForm {

	private Map<Integer, PageElement> quesMap = new LinkedHashMap<Integer, PageElement>();

	private Integer currentQuesNo;

	private String tabName;

	public String getTabName() {
		return tabName;
	}

	public void setTabName(String tabName) {
		this.tabName = tabName;
	}

	public Integer getCurrentQuesNo() {
		return currentQuesNo;
	}

	public void setCurrentQuesNo(Integer currentQuesNo) {
		this.currentQuesNo = currentQuesNo;
	}

	public Map<Integer, PageElement> getQuesMap() {
		return quesMap;
	}

	public void setQuesMap(Map<Integer, PageElement> quesMap) {
		this.quesMap = quesMap;
	}
}

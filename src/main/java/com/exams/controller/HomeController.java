package com.exams.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.exams.constants.RequestMappingConstants;

@Controller
public class HomeController {

	@RequestMapping(value = RequestMappingConstants.ONLINE_EXAMS_HOME, method = RequestMethod.GET)
	public String home() {
		return "home";
	}
}

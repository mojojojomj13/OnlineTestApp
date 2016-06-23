package com.exams.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.exams.constants.CommonConstants;
import com.exams.constants.RequestMappingConstants;
import com.exams.vo.User;

/**
 * @author Prithvish Mukherjee
 */
@Controller
public class SecurityController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityController.class);

	@RequestMapping(value = RequestMappingConstants.ADMIN_URL, method = RequestMethod.GET)
	public ModelAndView adminPage(@ModelAttribute(CommonConstants.MODEL_ATTR_MSG) String msg,
			@ModelAttribute(CommonConstants.MODEL_ATTR_ERR) String error) {
		ModelAndView model = new ModelAndView();
		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.addObject("user", user);
		} catch (Exception e) {
			error = "true";
			msg = "Exception while retrieving the userName";
		}
		model.addObject(CommonConstants.MODEL_ATTR_MSG, msg);
		model.addObject(CommonConstants.MODEL_ATTR_ERR, error);
		model.setViewName(RequestMappingConstants.ADMIN_VIEW);
		return model;
	}

	@RequestMapping(value = RequestMappingConstants.LOGIN_URL, method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = CommonConstants.MODEL_ATTR_ERR, required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {
		ModelAndView model = new ModelAndView();
		if (error != null)
			model.addObject(CommonConstants.MODEL_ATTR_ERR, "Invalid username and password!");
		if (logout != null)
			model.addObject(CommonConstants.MODEL_ATTR_MSG, "You've been logged out successfully.");
		model.setViewName(RequestMappingConstants.LOGIN_VIEW);
		return model;
	}

	// for 403 access denied page
	@RequestMapping(value = RequestMappingConstants._403_URL, method = RequestMethod.GET)
	public ModelAndView accesssDenied() {
		ModelAndView model = new ModelAndView();
		// check if user is login
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			LOGGER.info("UserDetails  : {}", new Object[] { userDetail });
			model.addObject(CommonConstants.MODEL_ATTR_USERNAME, userDetail.getUsername());
		}
		model.setViewName(RequestMappingConstants._403_VIEW);
		return model;
	}
}
package com.exams.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.exams.constants.CommonConstants;
import com.exams.constants.RequestMappingConstants;
import com.exams.dao.QuestionsDao;
import com.exams.domain.Question;
import com.exams.enums.STATUS;
import com.exams.exceptions.DatabaseException;
import com.exams.exceptions.ServiceException;
import com.exams.form.QuestionForm;
import com.exams.vo.PageElement;
import com.exams.vo.User;

/**
 * @author Prithvish Mukherjee
 */
@Controller
public class ExamsController {

	@Autowired
	QuestionsDao quesDao;

	private static final Logger LOGGER = LoggerFactory.getLogger(ExamsController.class);

	@RequestMapping(value = RequestMappingConstants.ADMIN_URL, method = { RequestMethod.GET })
	public ModelAndView adminPage(HttpSession session, @ModelAttribute(CommonConstants.MODEL_ATTR_MSG) String msg,
			@ModelAttribute(CommonConstants.MODEL_ATTR_ERR) String error) {
		ModelAndView mv = new ModelAndView();
		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			LOGGER.info("quesForm is null :: ");
			QuestionForm quesForm = new QuestionForm();
			quesForm.setQuesMap(getMap());
			quesForm.setCurrentQuesNo(1);
			quesForm.setTabName("tab2-content");
			user.setQuesForm(quesForm);
			if (null == session.getAttribute("user")) {
				mv.addObject("user", user);
				session.setAttribute("user", user);
				int n = Collections.frequency(sessionRegistry.getAllPrincipals(), user);
				for (int i = 1; i < n; i++) {
					sessionRegistry.getAllPrincipals().remove(user);
				}
			} else {
				mv.addObject("user", session.getAttribute("user"));
			}
		} catch (Exception e) {
			error = "true";
			msg = "Exception while retrieving the userName " + e;
		}
		mv.addObject(CommonConstants.MODEL_ATTR_MSG, msg);
		mv.addObject(CommonConstants.MODEL_ATTR_ERR, error);
		mv.setViewName(RequestMappingConstants.ADMIN_VIEW);
		return mv;
	}

	private Map<Integer, PageElement> getMap() throws ServiceException {
		Map<Integer, PageElement> quesMap = new LinkedHashMap<Integer, PageElement>();
		try {
			List<Question> quesList = quesDao.getAllQuestions();
			Random r = new Random();
			Collections.shuffle(quesList, r);
			int i = 1;
			for (Question q : quesList) {
				PageElement p = new PageElement();
				p.setDesc(q.getQuesText());
				LinkedHashMap<String, Boolean> ops = new LinkedHashMap<String, Boolean>();
				List<String> opsList = new ArrayList<String>();
				opsList.add(q.getOpA());
				opsList.add(q.getOpB());
				opsList.add(q.getOpC());
				opsList.add(q.getOpD());
				Collections.shuffle(opsList, r);
				ops.put(opsList.get(0), false);
				ops.put(opsList.get(1), false);
				ops.put(opsList.get(2), false);
				ops.put(opsList.get(3), false);
				p.setInputType("radio");
				if (q.getCorrectOps().size() > 1)
					p.setInputType("checkbox");
				p.setOps(ops);
				p.setUserAns(new ArrayList<String>());
				if (!quesMap.containsValue(p)) {
					quesMap.put(new Integer(i++), p);
				}
				if (q.getQuesImg() != null) {
					p.setImgSrc(new String(q.getQuesImg(), "UTF-8"));
				}
			}
			return quesMap;
		} catch (DatabaseException | UnsupportedEncodingException e) {
			throw new ServiceException(STATUS.INTERNAL_SERVER_ERROR, e);
		}
	}

	@RequestMapping(value = "/createQuestion", method = RequestMethod.POST)
	public ModelAndView createQuestion(HttpSession session, @Valid @ModelAttribute Question question,
			BindingResult errors, @RequestParam(required = true, name = "tabName") String tabName,
			@RequestParam(value = "file", required = false) Part file) {
		ModelAndView mv = new ModelAndView();
		try {
			List<String> errorMsg = new ArrayList<String>();
			initQuesFormAndUser(session, mv, tabName);
			if (errors.hasErrors()) {
				getvalidationErrors(errors, mv);
				mv.setViewName(RequestMappingConstants.ADMIN_VIEW);
				return mv;
			}
			LOGGER.info("file  :: " + (null == file));
			if (!"".equals(file.getSubmittedFileName().trim()) && !validatePart(file, errorMsg)) {
				StringBuffer buff = new StringBuffer();
				buff.append("error in uploading image :: ");
				for (String str : errorMsg) {
					buff.append(str);
				}
				handleException(mv, new Exception(buff.toString()));
				mv.setViewName(RequestMappingConstants.ADMIN_VIEW);
				return mv;
			}
			if ("".equals(file.getSubmittedFileName().trim())
					&& question.getQuesText().trim().equalsIgnoreCase("Not Applicable")) {
				handleException(mv,
						new Exception("When you dont have an image, the Question Text cannot be 'Not Applicable' "));
				mv.setViewName(RequestMappingConstants.ADMIN_VIEW);
				return mv;
			}
			if (!"".equals(file.getSubmittedFileName().trim())
					&& !question.getQuesText().trim().equalsIgnoreCase("Not Applicable")) {
				handleException(mv,
						new Exception("When you have an Image then Question Text must be 'Not Applicable' strictly"));
				mv.setViewName(RequestMappingConstants.ADMIN_VIEW);
				return mv;
			}
			LOGGER.info("quesImg ::" + file);
			LOGGER.info("uploaded some files...");
			byte[] b = new byte[(int) file.getSize()];
			if (file.getInputStream().read(b) != -1) {
				question.setQuesImg(Base64.getEncoder().encode(b));
			}
			List<String> options = new ArrayList<String>();
			for (String op : question.getCorrectOps()) {
				switch (op) {
				case "opA":
					options.add(question.getOpA());
					break;
				case "opB":
					options.add(question.getOpB());
					break;
				case "opC":
					options.add(question.getOpC());
					break;
				case "opD":
					options.add(question.getOpD());
					break;
				default:
					break;
				}
			}
			question.setCorrectOps(options);
			quesDao.addQuestions(question);
			mv.addObject("question", new Question());
			mv.addObject(CommonConstants.MODEL_ATTR_MSG, "Question created successfully");
			mv.addObject(CommonConstants.MODEL_ATTR_ERR, "false");
		} catch (IOException | DatabaseException | ServiceException e) {
			handleException(mv, e);
		}
		mv.setViewName(RequestMappingConstants.ADMIN_VIEW);
		return mv;
	}

	@RequestMapping(value = "/createQuestion", method = RequestMethod.GET)
	public ModelAndView createQuesGet(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		mv.addObject(CommonConstants.MODEL_ATTR_MSG, "GET method not allowed");
		mv.addObject(CommonConstants.MODEL_ATTR_ERR, "true");
		try {
			if (null != session.getAttribute("user"))
				initQuesFormAndUser(session, mv);
		} catch (Exception e) {
			handleException(mv, e);
		}
		mv.setViewName(RequestMappingConstants.ADMIN_VIEW);
		return mv;
	}

	private boolean validatePart(Part file, List<String> error) throws ServiceException {
		boolean isValid = true;
		try {
			if (null != file) {
				long size = file.getSize();
				long sizeInKb = size / 1024;
				if (sizeInKb >= 1024) {
					LOGGER.info(sizeInKb + " , max size is 1 MB");
					error.add(sizeInKb + " , max size is 1 MB");
					isValid = false;
				}
				if (!file.getSubmittedFileName().contains(".png") && !file.getSubmittedFileName().contains(".gif")
						&& !file.getSubmittedFileName().contains(".jpg")
						&& !file.getSubmittedFileName().contains(".jpeg")) {
					LOGGER.info(file.getSubmittedFileName() + " , valid extension file missing");
					error.add(file.getSubmittedFileName() + " , valid extension file missing");
					isValid = false;
				}
				if (!file.getContentType().contains("image/")) {
					LOGGER.info(file.getContentType() + " , Valid content type missing");
					error.add(file.getContentType() + " ,  Valid content type missing");
					isValid = false;
				}
			}
		} catch (Exception e) {
			throw new ServiceException(STATUS.BAD_REQUEST, "The File uploaded is INVALID ::" + e.toString(), e);
		}
		return isValid;
	}

	/**
	 * @param session
	 * @param mv
	 */
	private void initQuesFormAndUser(HttpSession session, ModelAndView mv) {
		mv.addObject("user", session.getAttribute("user"));
	}

	private void initQuesFormAndUser(HttpSession session, ModelAndView mv, String tabName) {
		((User) session.getAttribute("user")).getQuesForm().setTabName(tabName);
		mv.addObject("user", session.getAttribute("user"));
	}

	/**
	 * @param mv
	 * @param e
	 */
	private void handleException(ModelAndView mv, Exception e) {
		String error = "true";
		String msg = "Exception in Controller " + e;
		mv.addObject(CommonConstants.MODEL_ATTR_MSG, msg);
		mv.addObject(CommonConstants.MODEL_ATTR_ERR, error);
	}

	private void getvalidationErrors(BindingResult errors, ModelAndView mv) {
		String error = "true";
		String msg = "Validation errors ::  ";
		for (ObjectError err : errors.getAllErrors()) {
			msg += err.getDefaultMessage();
		}
		mv.addObject(CommonConstants.MODEL_ATTR_MSG, msg);
		mv.addObject(CommonConstants.MODEL_ATTR_ERR, error);
	}

	private boolean isValid(List<String> userAns, PageElement currPage) throws ServiceException {
		boolean isValid = false;
		for (String op : userAns) {
			try {
				Integer.parseInt(op);
			} catch (NumberFormatException e) {
				return false;
			}
		}
		int i = 1;
		@SuppressWarnings("unchecked")
		LinkedHashMap<String, Boolean> tempMap = (LinkedHashMap<String, Boolean>) currPage.getOps().clone();
		Iterator<Entry<String, Boolean>> it = tempMap.entrySet().iterator();
		currPage.getOps().clear();
		currPage.getUserAns().clear();
		while (it.hasNext()) {
			Entry<String, Boolean> op = it.next();
			String key = String.valueOf(i);
			if (userAns.contains(key)) {
				currPage.getOps().put(op.getKey(), true);
				currPage.getUserAns().add(op.getKey());
			} else {
				currPage.getOps().put(op.getKey(), false);
			}
			i++;
		}
		isValid = true;
		return isValid;
	}

	@RequestMapping(value = "/submitForm", method = RequestMethod.POST)
	public ModelAndView submitForm(HttpSession session,
			@RequestParam(defaultValue = "1", required = true, value = "quesNo") Integer quesNo,
			@RequestParam(required = false, name = "userAns") List<String> userAns,
			@RequestParam(required = true, name = "currQues") Integer currQues,
			@RequestParam(required = true, name = "tabName") String tabName) {
		ModelAndView mv = new ModelAndView();
		try {
			QuestionForm quesForm = ((User) session.getAttribute("user")).getQuesForm();
			quesForm.setCurrentQuesNo(quesNo);
			quesForm.setTabName(tabName);
			LOGGER.info("quesForm :" + quesForm);
			LOGGER.info("userAns :: " + userAns);
			LOGGER.info("quesNo :: " + quesNo);
			LOGGER.info("tabName ::" + tabName);
			PageElement currPageEle = quesForm.getQuesMap().get(currQues);
			LOGGER.info("Current Page ops :: " + currPageEle.getOps());
			if (null != userAns && isValid(userAns, currPageEle)) {
				LOGGER.info("user has selected, Marked his answer...");
				currPageEle.setIsAttempted(true);
			} else {
				if (currPageEle.getInputType().equals("checkbox")) {
					for (String op : currPageEle.getOps().keySet()) {
						currPageEle.getOps().put(op, false);
					}
					currPageEle.getUserAns().clear();
					currPageEle.setIsAttempted(false);
				}
				LOGGER.info("No User Interaction...");
			}
			((User) session.getAttribute("user")).setQuesForm(quesForm);
			initQuesFormAndUser(session, mv, tabName);
			mv.setViewName(RequestMappingConstants.ADMIN_VIEW);
		} catch (ServiceException e) {
			handleException(mv, e);
		}
		return mv;
	}

	@RequestMapping(value = "/submitForm", method = RequestMethod.GET)
	public ModelAndView submitFormGet(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		try {
			mv.addObject(CommonConstants.MODEL_ATTR_MSG, "GET method not allowed");
			mv.addObject(CommonConstants.MODEL_ATTR_ERR, "true");
			if (null != session.getAttribute("user"))
				initQuesFormAndUser(session, mv);
		} catch (Exception e) {
			handleException(mv, e);
		}
		mv.setViewName(RequestMappingConstants.ADMIN_VIEW);
		return mv;
	}

	@Autowired
	private SessionRegistry sessionRegistry;

	@RequestMapping(value = RequestMappingConstants.LOGIN_URL, method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = CommonConstants.MODEL_ATTR_ERR, required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, HttpSession session) {
		ModelAndView model = new ModelAndView();
		if (error != null) {
			handleSession(session);
			model.addObject(CommonConstants.MODEL_ATTR_ERR, "Invalid username and password!");
		}
		if (logout != null) {
			handleSession(session);
			model.addObject(CommonConstants.MODEL_ATTR_MSG, "You've been logged out successfully.");
		}
		model.setViewName(RequestMappingConstants.LOGIN_VIEW);
		return model;
	}

	/**
	 * @param session
	 */
	private void handleSession(HttpSession session) {
		if (null != session.getAttribute("user"))
			session.removeAttribute("user");
		session.invalidate();
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
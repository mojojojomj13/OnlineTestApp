package com.exams.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.exams.constants.CommonConstants;
import com.exams.constants.RequestMappingConstants;
import com.exams.exceptions.InvalidRequestException;
import com.exams.exceptions.ServiceException;
import com.exams.vo.ResponseVOBuilder;
import com.exams.vo.ResponseVOBuilder.OnlineExamResponseVO;

/**
 * 
 * This class handles the error from the controller other than Service exception
 * and returns a corresponding {@link ResponseEntity}
 * 
 * @author Prithvish Mukherjee
 *
 */
@ControllerAdvice
public class OnlineExamsControllerAdvice {

	private static final Logger LOGGER = LoggerFactory.getLogger(OnlineExamsControllerAdvice.class);

	@ExceptionHandler({ HttpMessageNotReadableException.class, InvalidRequestException.class })
	public ResponseEntity<OnlineExamResponseVO> handleInternalUnreadableMessageError(Exception ex) {
		LOGGER.error("Error in the reading http message,  " + HttpStatus.BAD_REQUEST.value() + " :: " + ex);
		return new ResponseEntity<OnlineExamResponseVO>(new ResponseVOBuilder().code(HttpStatus.BAD_REQUEST.value())
				.message("Error in Controller  :: " + ex).build(), HttpStatus.BAD_REQUEST);
	}

	/**
	 * This method handles the Controller scenarios where there is any Exception
	 * other than {@link ServiceException} and returns a INTERNAL SERVER ERROR
	 * response back to the caller
	 * 
	 * @param ex
	 *            The {@link Exception} which was thrown in the controller
	 * @return the response wrapped in a {@link ResponseEntity} sent back to the
	 *         caller
	 */
	@ExceptionHandler({ Exception.class, RuntimeException.class, HttpRequestMethodNotSupportedException.class })
	public ModelAndView handleInternalServerError(Exception ex) {
		ModelAndView model = new ModelAndView();
		ex.printStackTrace();
		if (ex != null) {
			model.addObject(CommonConstants.MODEL_ATTR_MSG, "Server Error  :: " + ex.toString());
			model.addObject(CommonConstants.MODEL_ATTR_ERR, "true");
		}
		model.setViewName(RequestMappingConstants.ADMIN_VIEW);
		return model;
	}
}

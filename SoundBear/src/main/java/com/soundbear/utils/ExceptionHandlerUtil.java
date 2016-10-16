package com.soundbear.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionHandlerUtil {


	@ExceptionHandler(Exception.class)
	public ModelAndView   onException(Exception e, HttpServletRequest request, HttpServletResponse response , Model model) {

		ModelAndView mav = new ModelAndView();

		mav.addObject("exception", e);
		System.err.println(e.getMessage());
		
		return mav;

	}

}

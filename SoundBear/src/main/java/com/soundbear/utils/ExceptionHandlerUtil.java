package com.soundbear.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionHandlerUtil {


	@ExceptionHandler(MultipartException.class)
	public ModelAndView   onException(Exception e, HttpServletRequest request, HttpServletResponse response , Model model) {

		
		ModelAndView mav = new ModelAndView();

		if (e instanceof MultipartException) {
			request.setAttribute(ErrorMsgs.ERROR_REDIRECT, Pages.REGISTER);
			request.setAttribute(ErrorMsgs.ERROR_MSG, ErrorMsgs.LIMIT_EXCEEDED);
		}
		mav.addObject("exception", e);
//	    mav.addObject("url", req.getRequestURL());
	    mav.setViewName("error");
//		mav.addObject("name", e.getClass().getSimpleName());
//		mav.addObject("message", e.getMessage());
		System.err.println("1111111111111111111111111111" + e.getMessage() + "==============1111111111111111111111111111====================");
//		
//		 try {
//			response.sendRedirect("/error");
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}  
		
		return mav;

	}

}

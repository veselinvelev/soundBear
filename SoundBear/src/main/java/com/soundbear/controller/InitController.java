package com.soundbear.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.soundbear.model.app.User;
import com.soundbear.utils.Pages;

@Controller
public class InitController {
	@Autowired
	private HttpSession session;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String login(Model model) {
		return getPage(Pages.PLAY);
	}

	@RequestMapping(value = "/play", method = RequestMethod.GET)
	public String play(Model model) {
		return getPage(Pages.PLAY);
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register(Model model) {
		//return getPage(Pages.PLAY);
		return "register";
	}

	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String upload(Model model) {
		return getPage(Pages.UPLOAD);
	}
	

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String profile(Model model) {
		return getPage(Pages.PROFILE);
	}

	private String getPage(String page) {
		User user = (User) session.getAttribute(UserController.LOGGED_USER);
		return (user != null) ? page : Pages.LOGIN;

	}

}

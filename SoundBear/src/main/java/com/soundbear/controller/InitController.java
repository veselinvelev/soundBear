package com.soundbear.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.soundbear.model.app.User;
import com.soundbear.repository.UserDAO;
import com.soundbear.utils.Pages;

@Controller
public class InitController {
	@Autowired
	private HttpSession session;
	@Autowired
	private UserDAO userRepository;

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
		
		User user = (User) session.getAttribute(UserController.LOGGED_USER);
		user.setFollowers(userRepository.getNumFollowers(user)); ;
		user.setFollowing(userRepository.getNumFollowing(user));
		
		return getPage(Pages.PROFILE);
	}
	
	@RequestMapping(value = "/mySongs", method = RequestMethod.GET)
	public String mySongs(Model model) {
		return getPage(Pages.MYSONGS);
	}
	
	@RequestMapping(value = "/error", method = RequestMethod.POST)
	public String errorPost(Model model) {
		return Pages.ERROR;
	}
	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public String errorGetS(Model model) {
		return Pages.ERROR;
	}

	@RequestMapping(value = "/followers", method = RequestMethod.GET)
	public String followers(Model model) {
		return Pages.FOLLOWERS;
	}

	@RequestMapping(value = "/viewProfile", method = RequestMethod.GET)
	public String viewProfile(Model model, HttpServletRequest req) {

		int id = Integer.parseInt(req.getParameter("id"));

		System.err.println("PROFILEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE" + "           " + id);

		User user = userRepository.getUserById(id);
		
		req.setAttribute("anotherUser", user);

		return "another_user";
	}

	private String getPage(String page) {
		User user = (User) session.getAttribute(UserController.LOGGED_USER);
		return (user != null) ? page : Pages.LOGIN;

	}

}

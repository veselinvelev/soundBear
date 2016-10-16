package com.soundbear.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.soundbear.model.app.User;
import com.soundbear.repository.UserDAO;
import com.soundbear.utils.Pages;
import com.soundbear.utils.UserUtil;
import com.soundbear.utils.ValidatorUtil;

@Controller
public class InitController {
	@Autowired
	private HttpSession session;
	@Autowired
	private UserDAO userRepository;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Model model) {
		return getPage(Pages.PLAY);
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		return getPage(Pages.PLAY);
	}
	@RequestMapping(value = "/showSongInfo", method = RequestMethod.GET)
	public String songInfo(Model model) {
		return getPage(Pages.PLAY);
	}

	@RequestMapping(value = "/play", method = RequestMethod.GET)
	public String play(Model model) {
		return getPage(Pages.PLAY);
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register(Model model) {
		return Pages.REGISTER;
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String search(Model model) {
		return getPage(Pages.SEARCH);
	}

	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String upload(Model model) {
		return getPage(Pages.UPLOAD);
	}

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String profile(Model model) {

		if (ValidatorUtil.isSessionOver(session)) {
			return Pages.LOGIN;
		}

		User user = (User) session.getAttribute(UserUtil.LOGGED_USER);
		user.setFollowers(userRepository.getNumFollowers(user));
		user.setFollowing(userRepository.getNumFollowing(user));

		return getPage(Pages.PROFILE);
	}

	@RequestMapping(value = "/mySongs", method = RequestMethod.GET)
	public String mySongs(Model model) {
		return getPage(Pages.MYSONGS);
	}

	@RequestMapping(value = "/playlists", method = RequestMethod.GET)
	public String playlists(Model model) {
		return getPage(Pages.PLAYLISTS);
	}
	


	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public String errorGetS(Model model) {
		return getPage(Pages.ERROR);
	}

	@RequestMapping(value = "/follow", method = RequestMethod.GET)
	public String followers(Model model, HttpServletRequest req) {
		if (ValidatorUtil.isSessionOver(session)) {
			return Pages.LOGIN;
		}

		req.setAttribute("item", req.getParameter("item"));

		return getPage(Pages.FOLLOWERS);
	}

	@RequestMapping(value = "/viewProfile", method = RequestMethod.GET)
	public String viewProfile(Model model, HttpServletRequest req) {
		
		if (ValidatorUtil.isSessionOver(session)) {
			return Pages.LOGIN;
		}

		int id = Integer.parseInt(req.getParameter("id"));

		User user = userRepository.getUserById(id);

		req.setAttribute("anotherUser", user);

		return getPage(Pages.ANOTHEUSER);
		
	}
	
	@RequestMapping(value = "/changePassword", method = RequestMethod.GET)
	public String changePassword(Model model) {
		 return getPage(Pages.PROFILE);
	}
	@RequestMapping(value = "/photoUpload", method = RequestMethod.GET)
	public String photoUpload(Model model) {
		 return getPage(Pages.PROFILE);
	}
	
	@RequestMapping(value = "/validateRegisterForm", method = RequestMethod.GET)
	public String validateRegisterForm(Model model) {
		return Pages.LOGIN;
	}

	@RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
	public String resetPassword(Model model) {
		return Pages.LOGIN;
	}
	@RequestMapping(value = "/songUpload", method = RequestMethod.GET)
	public String songUpload(Model model) {
		 return getPage(Pages.UPLOAD);
	}
	@RequestMapping(value = "/registerSubmit", method = RequestMethod.GET)
	public String registerSubmit(Model model) {

		return Pages.LOGIN;
	}

	private String getPage(String page) {
		User user = (User) session.getAttribute(UserUtil.LOGGED_USER);
		return (user != null) ? page : Pages.LOGIN;

	}



}

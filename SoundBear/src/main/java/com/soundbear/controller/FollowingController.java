package com.soundbear.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.soundbear.model.app.User;
import com.soundbear.model.json.reponse.BaseResponse;
import com.soundbear.model.json.reponse.BaseResponse.ResponseStatus;
import com.soundbear.model.json.reponse.FollowersResponse;
import com.soundbear.repository.UserDAO;
import com.soundbear.utils.Pages;
import com.soundbear.utils.UserUtil;
import com.soundbear.utils.ValidatorUtil;

@RestController
public class FollowingController {
	
	@Autowired
	private UserDAO userRepository;
	@Autowired
	private HttpSession session;
	
	
	@RequestMapping(value = "/updateFollow", method = RequestMethod.GET)
	public  BaseResponse updateFollow(HttpServletRequest req, HttpServletResponse resp) {

		

		if (ValidatorUtil.isSessionOver(session)) {
			try {
				resp.sendRedirect(Pages.LOGIN);
				return null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		User user = (User) session.getAttribute(UserUtil.LOGGED_USER);
		int id = Integer.parseInt(req.getParameter("id"));
		String action = req.getParameter("action");

		if (action.equals("follow")) {
			userRepository.follow(user, id);
		} else {
			userRepository.unfollow(user, id);
		}
		BaseResponse response = new BaseResponse();
		response.setStatus(ResponseStatus.OK);
		return response;
	}
	
	@RequestMapping(value = "/checkFollowStatus", method = RequestMethod.GET)
	public  BaseResponse checkFollowStatus(HttpServletRequest req, HttpServletResponse resp) {

		User user = (User) session.getAttribute(UserUtil.LOGGED_USER);

		if (ValidatorUtil.isSessionOver(session)) {
			try {
				resp.sendRedirect(Pages.LOGIN);
				return null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		int id = Integer.parseInt(req.getParameter("id"));

		int followStatus = 0;
		ResponseStatus status = ResponseStatus.NO;

		followStatus = userRepository.getFollowStatus(user, id);

		if (followStatus != 0) {
			status = ResponseStatus.OK;
		}

		BaseResponse response = new BaseResponse();
		response.setStatus(status);
		return response;
	}
	
	
	@RequestMapping(value = "/listFollowers", method = RequestMethod.GET)
	public  FollowersResponse listFollowers(HttpServletRequest req, HttpServletResponse resp) {

		User user = (User) session.getAttribute(UserUtil.LOGGED_USER);

		if (ValidatorUtil.isSessionOver(session)) {
			try {
				resp.sendRedirect(Pages.LOGIN);
				return null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		ArrayList<User> followers = null;
		if (req.getParameter("item").equals("Followers")) {
			followers = userRepository.listFollowers(user);
		} else {
			followers = userRepository.listFollowing(user);
		}

		FollowersResponse response = new FollowersResponse();
		
		UserUtil.maskPassword(followers);

		response.setFollowers(followers);

		return response;
	}

}

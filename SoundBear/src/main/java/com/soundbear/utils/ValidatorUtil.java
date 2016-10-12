package com.soundbear.utils;

import javax.servlet.http.HttpSession;

import com.soundbear.controller.UserController;
import com.soundbear.model.app.User;

public class ValidatorUtil {

	public static boolean isStringValid(String str) {

		return ((str != null) && (str.length() > 0));
	}
	public static boolean isSessionOver(HttpSession session) {

		User user = (User) session.getAttribute(UserController.LOGGED_USER);
		if (user == null) {
			return true;
		}
		return false;
	}
}

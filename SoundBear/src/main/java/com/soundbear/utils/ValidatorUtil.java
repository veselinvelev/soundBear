package com.soundbear.utils;

import javax.servlet.http.HttpSession;

import com.soundbear.model.app.User;
import com.soundbear.model.json.reponse.BaseResponse;
import com.soundbear.model.json.reponse.BaseResponse.ResponseStatus;

public class ValidatorUtil {
	private static final String INVALID_PASSWORD = "Invalid Password";

	private static final String PASSWORD_REGEX = "[a-zA-Z0-9_.!@#$%^&*()]*$";
	private static final String USRNAME_TOO_SHORT = "The username is too short";

	private static final String INVALID_USERNAME = "Username must contain only latin letters and digits, must not start with a digit.";

	private static final String INVALID_EMAIL = "Email is not in a valid format.";
	private static final int MIN_USERNAME_LENGTH = 1;

	private static final String THERE_ARE_EMPTY_FIELDS = "Please fill out all fields";
	private static final String USERNAME_REGEX = "^[a-zA-Z]+[a-zA-Z0-9_.]*$";
	public static final String PASSWRDS_DONT_MATCH = "Passwords are different";
	public static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	public static boolean isStringValid(String str) {

		return ((str != null) && (str.length() > 0));
	}

	public static boolean isSessionOver(HttpSession session) {

		User user = (User) session.getAttribute(UserUtil.LOGGED_USER);
		if (user == null) {
			return true;
		}
		return false;
	}

	/*
	 * Implemented in navbar.jsp via scriplet public static void
	 * noBackspaceLogin(HttpServletResponse response) {
	 * 
	 * response.setHeader("Cache-Control",
	 * "no-cache, no-store, must-revalidate"); // HTTP
	 * response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
	 * response.setDateHeader("Expires", 0); }
	 */

	public static void checkInput(String username, String password1, String password2, String email,
			BaseResponse response) {
		if (!ValidatorUtil.isStringValid(username) || !ValidatorUtil.isStringValid(password1)
				|| !ValidatorUtil.isStringValid(password2) || !ValidatorUtil.isStringValid(email)) {
			response.setStatus(ResponseStatus.NO);
			response.setMsg(THERE_ARE_EMPTY_FIELDS);
		}
	}

	public static void checkUsernam(String username, BaseResponse response) {
		if (username.length() < MIN_USERNAME_LENGTH) {
			response.setStatus(ResponseStatus.NO);
			response.setMsg(USRNAME_TOO_SHORT);
		}

		if (!username.matches(USERNAME_REGEX)) {
			response.setStatus(ResponseStatus.NO);
			response.setMsg(INVALID_USERNAME);
		}
	}

	public static void checkEmail(String email, BaseResponse response) {
		if (!email.matches(EMAIL_REGEX)) {
			response.setStatus(ResponseStatus.NO);
			response.setMsg(INVALID_EMAIL);
		}
	}

	public static void checkPassword(String password1, String password2, BaseResponse response) {
		if (!password1.equals(password2)) {
			response.setStatus(ResponseStatus.NO);
			response.setMsg(PASSWRDS_DONT_MATCH);
		}

		if (!password1.matches(PASSWORD_REGEX)) {
			response.setStatus(ResponseStatus.NO);
			response.setMsg(INVALID_PASSWORD);
		}
	}
}

package com.soundbear.controller;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.soundbear.model.app.User;
import com.soundbear.model.app.exceptions.UserException;
import com.soundbear.model.json.reponse.BaseResponse;
import com.soundbear.model.json.reponse.BaseResponse.ResponseStatus;
import com.soundbear.model.json.reponse.RegisterFormResponse;
import com.soundbear.model.json.request.LoginRequest;
import com.soundbear.model.json.request.ResetPasswordRequest;
import com.soundbear.repository.UserRepository;
import com.soundbear.utils.DBCleaner;
import com.soundbear.utils.EmailUtil;
import com.soundbear.utils.EncryptionUtil;
import com.soundbear.utils.ErrorMsgs;
import com.soundbear.utils.Pages;
import com.soundbear.utils.ValidatorUtil;

@Controller
public class UserController {

	private static final String USRNAME_TOO_SHORT = "The username is too short";

	private static final String INVALID_USERNAME = "Username must contain only latin letters and digits, must not start with a digit.";

	private static final String INVALID_EMAIL = "Email is not in a valid format.";

	private static final String PASSWRDS_DONT_MATCH = "Passwords are different";

	private static final String ACC_ACTIVATION = "SoundBear Activation";

	private static final String ACTIVATION_MSG = "To activate the account: ";

	private static final String REGISTRATION_FAILED = "We can not register you right now. Please try again later";

	private static final String REGISTRATION_SUCCESSFUL = "Registration successful. Please check your email for account activation.";

	private static final int MIN_USERNAME_LENGTH = 4;

	private static final String THERE_ARE_EMPTY_FIELDS = "Please fill out all fields";

	public static final String LOGGED_USER = "loggedUser";

	private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String RESET_MSG = "Your password has been reset to : ";
	private static final String PASSWORD_RESET = "Password Reset";
//	private static final int VALID_STRING_LENGTH = 45;
//	private static final int INTERVAL_OF_DB_CLEAN = 10800000;
	private static final String USERNAME_REGEX = "^[a-zA-Z]+[a-zA-Z0-9_]*$";

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private DBCleaner dbCleaner;
	@Autowired
	private HttpSession session;

	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	public @ResponseBody BaseResponse resetPassword(@RequestBody ResetPasswordRequest request) {
		String email = request.getEmail();

		System.out.println(email);
		System.out.println(email.isEmpty());
		System.out.println(email.matches(EMAIL_REGEX));
		System.out.println(userRepository.isValidEmail(email));

		boolean isEmpty = email.isEmpty();
		boolean isValid = email.matches(EMAIL_REGEX);
		boolean isFree = userRepository.isValidEmail(email);

		ResponseStatus status = null;

		if (isEmpty || !isValid || isFree) {
			status = ResponseStatus.NO;
		}
		else {
			status = ResponseStatus.OK;
			final String newPassword = genPassword();
			userRepository.updatePassword(email, newPassword);

			new Thread() {
				public void run() {
					try {
						EmailUtil.sendEmail(email, RESET_MSG + newPassword, PASSWORD_RESET);
					}
					catch (AddressException e) {
						e.printStackTrace();
					}
					catch (MessagingException e) {
						e.printStackTrace();
					}
				};
			}.start();
		}

		BaseResponse response = new BaseResponse();
		response.setStatus(status);

		return response;

	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody BaseResponse login(@RequestBody LoginRequest request, Model model) {

		if (!dbCleaner.isAlive()) {
			dbCleaner.start();
		}

		ResponseStatus status = null;
		String username = null;
		String password = null;

		if (request != null) {
			username = request.getUsername();

			password = request.getPassword1();
		}

		User user = null;

		boolean isUsernameValid = ValidatorUtil.isStringValid(username);
		boolean isPasswordValid = ValidatorUtil.isStringValid(password);
		if (isUsernameValid && isPasswordValid) {

			user = userRepository.getUser(username, password);
			if (user != null) {

				session.setAttribute(LOGGED_USER, user);
				status = ResponseStatus.OK;
			}
			else {
				status = ResponseStatus.NO;
			}
		}
		else {
			status = ResponseStatus.NO;
		}
		BaseResponse response = new BaseResponse();
		response.setStatus(status);
		return response;
	}

	@RequestMapping(value = "/registerSubmit", method = RequestMethod.POST)
	public @ResponseBody BaseResponse register(@RequestBody LoginRequest request, HttpServletRequest httpRequest) {

		// if (request != null) {
		String username = request.getUsername();
		String password1 = request.getPassword1();
		String password2 = request.getPassword2();
		String email = request.getEmail();
		// }

		BaseResponse response = new BaseResponse();
		ResponseStatus status;
		String msg = null;

		if (!ValidatorUtil.isStringValid(username) || !ValidatorUtil.isStringValid(password1)
				|| !ValidatorUtil.isStringValid(password2) || !ValidatorUtil.isStringValid(email)) {
			response.setStatus(ResponseStatus.NO);
			response.setMsg(THERE_ARE_EMPTY_FIELDS);
			return response;
		}

		if (!password1.equals(password2)) {
			response.setStatus(ResponseStatus.NO);
			response.setMsg(PASSWRDS_DONT_MATCH);
			return response;
		}

		if (username.length() < MIN_USERNAME_LENGTH) {
			response.setStatus(ResponseStatus.NO);
			response.setMsg(USRNAME_TOO_SHORT);
			return response;
		}

		if (!username.matches(USERNAME_REGEX)) {
			response.setStatus(ResponseStatus.NO);
			response.setMsg(INVALID_USERNAME);
			return response;
		}

		if (!email.matches(EMAIL_REGEX)) {
			response.setStatus(ResponseStatus.NO);
			response.setMsg(INVALID_EMAIL);
			return response;
		}

		// TODO
		// VALIDATE PASSWORD FORMAT AND LENGTH

		int success = 0;
		try {
			success = userRepository.addUser(new User(0, username, email, password1, 0, new Date()));
		}
		catch (UserException e1) {
			// TODO Auto-generated catch block
			System.out.println("User can not be created");
			e1.printStackTrace();
		}

		if (success != 0) {
			status = ResponseStatus.OK;
			msg = REGISTRATION_SUCCESSFUL;
			String activationURL = generateActivationURL(httpRequest, username);
			new Thread() {
				@Override
				public void run() {
					try {
						EmailUtil.sendEmail(email, ACTIVATION_MSG + activationURL, ACC_ACTIVATION);
					}
					catch (AddressException e) {
						e.printStackTrace();
					}
					catch (MessagingException e) {
						e.printStackTrace();
					}
				};
			}.start();

		}
		else {
			status = ResponseStatus.NO;
			msg = REGISTRATION_FAILED;
		}
		response.setStatus(status);
		response.setMsg(msg);

		return response;

	}

	@RequestMapping(value = "/validateRegisterForm", method = RequestMethod.POST)
	public @ResponseBody RegisterFormResponse validateRegisterForm(@RequestBody LoginRequest request) {

		String email = null;
		String username = null;

		if (request != null) {
			username = request.getUsername();
			email = request.getEmail();
		}

		boolean isEmailValid = ValidatorUtil.isStringValid(email);
		boolean isUsernameValid = ValidatorUtil.isStringValid(username);

		boolean isUsernameFree = false;
		boolean isEmailFree = false;

		if (isEmailValid && email.matches(EMAIL_REGEX)) {
			isEmailFree = userRepository.isValidEmail(email);
		}

		if (isUsernameValid) {
			isUsernameFree = userRepository.isValidUsername(username);
		}

		RegisterFormResponse response = new RegisterFormResponse();
		response.setValidEmail(isEmailFree);
		response.setValidUsername(isUsernameFree);

		return response;
	}

	@RequestMapping(value = "/activation", method = RequestMethod.GET)
	public String activateAcc(HttpServletRequest request) {

		String encryptUsername = request.getParameter("data").replace(' ', '+');
		String username = EncryptionUtil.decrypt(encryptUsername);

		String returnValue = null;

		User user = userRepository.getUserByName(username);

		if (user != null) {
			// Update DB
			if (!user.isActive()) {
				userRepository.updateActiveStatus(username);
				HttpSession session = request.getSession();
				session.setAttribute(LOGGED_USER, user);
				returnValue = Pages.PLAY;

			}
			else {
				returnValue = Pages.LOGIN;
			}

		}
		else {

			returnValue = Pages.ERROR;
			request.setAttribute(ErrorMsgs.ERROR_REDIRECT, Pages.REGISTER);
			request.setAttribute(ErrorMsgs.ERROR_MSG, ErrorMsgs.ACTIVATION_FAILED);
		}

		return returnValue;
	}

	private String genPassword() {
		int length = 8;
		boolean useLetters = true;
		boolean useNumbers = true;

		return RandomStringUtils.random(length, useLetters, useNumbers);
	}

	private String generateActivationURL(HttpServletRequest httpRequest, String username) {
		String activationURL = httpRequest.getRequestURL().toString();
		activationURL = activationURL.replace(httpRequest.getRequestURI(), httpRequest.getContextPath());

		activationURL += "/activation?data=" + EncryptionUtil.encrypt(username);

		return activationURL;
	}
}

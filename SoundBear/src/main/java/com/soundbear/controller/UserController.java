package com.soundbear.controller;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.soundbear.model.app.User;
import com.soundbear.model.app.exceptions.UserException;
import com.soundbear.model.json.reponse.BaseResponse;
import com.soundbear.model.json.reponse.BaseResponse.ResponseStatus;
import com.soundbear.model.json.reponse.RegisterFormResponse;
import com.soundbear.model.json.request.LoginRequest;
import com.soundbear.model.json.request.ResetPasswordRequest;
import com.soundbear.repository.UserDAO;
import com.soundbear.utils.DBCleaner;
import com.soundbear.utils.EmailUtil;
import com.soundbear.utils.EncryptionUtil;
import com.soundbear.utils.UserUtil;
import com.soundbear.utils.ValidatorUtil;

@RestController
public class UserController {

	private static final String DEFAULT_STRING = "Default";

	private static final String PASSWORD_UPDATED = "Password Updated!";

	private static final String ACC_ACTIVATION = "SoundBear Activation";

	private static final String ACTIVATION_MSG = "To activate the account: ";

	private static final String REGISTRATION_FAILED = "We can not register you right now. Please try again later";

	private static final String REGISTRATION_SUCCESSFUL = "Registration successful. Please check your email for account activation.";

	private static final String RESET_MSG = "Your password has been reset to : ";
	private static final String PASSWORD_RESET = "Password Reset";

	@Autowired
	private UserDAO userRepository;
	@Autowired
	private DBCleaner dbCleaner;
	@Autowired
	private HttpSession session;

	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	public BaseResponse resetPassword(@RequestBody ResetPasswordRequest request) {
		String email = request.getEmail();

		User user = null;

		boolean isEmpty = email.isEmpty();
		boolean isValid = email.matches(ValidatorUtil.EMAIL_REGEX);
		boolean isFree = userRepository.isValidEmail(email);

		ResponseStatus status = null;

		if (isEmpty || !isValid || isFree) {
			status = ResponseStatus.NO;
		} else {

			status = ResponseStatus.OK;
			final String newPassword = genPassword();
			try {
				user = new User(0, DEFAULT_STRING, email, newPassword, 0, new Date(), DEFAULT_STRING);
			} catch (UserException e1) {
				e1.printStackTrace();
			}
			userRepository.updatePassword(user);

			new Thread(() -> sendEmail(email, RESET_MSG + newPassword, PASSWORD_RESET)).start();

		}

		BaseResponse response = new BaseResponse();
		response.setStatus(status);

		return response;

	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public BaseResponse login(@RequestBody LoginRequest request, Model model) {

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
				user.setFollowers(userRepository.getNumFollowers(user));
				user.setFollowing(userRepository.getNumFollowing(user));
				session.setAttribute(UserUtil.LOGGED_USER, user);

				status = ResponseStatus.OK;
			} else {
				status = ResponseStatus.NO;
			}
		} else {
			status = ResponseStatus.NO;
		}
		BaseResponse response = new BaseResponse();
		response.setStatus(status);
		return response;
	}

	@RequestMapping(value = "/registerSubmit", method = RequestMethod.POST)
	public BaseResponse register(@RequestBody LoginRequest request, HttpServletRequest httpRequest) {

		String username = request.getUsername();
		String password1 = request.getPassword1();
		String password2 = request.getPassword2();
		String email = request.getEmail();

		BaseResponse response = new BaseResponse();
		ResponseStatus status;
		String msg = null;

		ValidatorUtil.checkInput(username, password1, password2, email, response);

		ValidatorUtil.checkUsernam(username, response);

		ValidatorUtil.checkEmail(email, response);

		ValidatorUtil.checkPassword(password1, password2, response);

		if (response.getStatus() == ResponseStatus.NO) {
			return response;
		}

		int success = 0;
		try {
			success = userRepository.addUser(new User(0, username, email, password1, 0, new Date(), null));
		} catch (UserException e1) {
			System.out.println("User can not be created");
			e1.printStackTrace();
		}

		if (success != 0) {
			status = ResponseStatus.OK;
			msg = REGISTRATION_SUCCESSFUL;
			String activationURL = generateActivationURL(httpRequest, username);
			new Thread(() -> sendEmail(email, ACTIVATION_MSG + activationURL, ACC_ACTIVATION)).start();

		} else {
			status = ResponseStatus.NO;
			msg = REGISTRATION_FAILED;
		}
		response.setStatus(status);
		response.setMsg(msg);

		return response;

	}

	@RequestMapping(value = "/validateRegisterForm", method = RequestMethod.POST)
	public RegisterFormResponse validateRegisterForm(@RequestBody LoginRequest request) {

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

		if (isEmailValid && email.matches(ValidatorUtil.EMAIL_REGEX)) {
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

	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public BaseResponse changePassword(@RequestBody LoginRequest request) {

		// if (request != null) {
		String password1 = request.getPassword1();
		String password2 = request.getPassword2();
		// }

		BaseResponse response = new BaseResponse();

		ValidatorUtil.checkPassword(password1, password2, response);
		
		if (response.getStatus() == ResponseStatus.NO) {
			return response;
		}

		

		User user = (User) session.getAttribute(UserUtil.LOGGED_USER);
		try {
			user.setPassword(password1);
		} catch (UserException e) {
			e.printStackTrace();
		}

		userRepository.updatePassword(user);
		response.setStatus(ResponseStatus.OK);
		response.setMsg(PASSWORD_UPDATED);

		return response;

	}

	private void sendEmail(String email, String activationURL, String subject) {
		try {
			EmailUtil.sendEmail(email, activationURL, subject);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}
}

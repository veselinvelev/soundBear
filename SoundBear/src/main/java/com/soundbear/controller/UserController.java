package com.soundbear.controller;

import static org.mockito.Matchers.booleanThat;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.annotation.ServletSecurity.EmptyRoleSemantic;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.soundbear.model.app.User;
import com.soundbear.model.json.reponse.BaseResponse;
import com.soundbear.model.json.reponse.BaseResponse.ResponseStatus;
import com.soundbear.model.json.reponse.RegisterFormResponse;
import com.soundbear.model.json.request.LoginRequest;
import com.soundbear.model.json.request.ResetPasswordRequest;
import com.soundbear.repository.UserRepository;
import com.soundbear.utils.EmailUtil;
import com.soundbear.utils.EncryptionUtil;

@Controller
public class UserController {
	private static final String ACC_ACTIVATION = "SoundBear Activation";

	private static final String ACTIVATION_MSG = "To activate the account: ";

	private static final String REGISTRATION_FAILED = "We can not register you right now. Please tyr again later";

	private static final String REGISTRATION_SUCCESSFUL = "Registration successful. Please check your email for account activation.";

	private static final int MIN_USERNAME_LENGTH = 4;

	private static final String THERE_ARE_EMPTY_FIELDS = "Please fill out all fields";

	private static final String LOGGED_USER = "loggedUser";

	private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String RESET_MSG = "Your password has been reset to : ";
	private static final String PASSWORD_RESET = "Password Reset";
	private static final int VALID_STRING_LENGTH = 45;

	@Autowired
	UserRepository userRepository;

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
		} else {
			status = ResponseStatus.OK;
			final String newPassword = genPassword();
			userRepository.updatePassword(email, newPassword);

			new Thread() {
				public void run() {
					try {
						EmailUtil.sendEmail(email, RESET_MSG + newPassword, PASSWORD_RESET);
					} catch (AddressException e) {
						e.printStackTrace();
					} catch (MessagingException e) {
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
	public @ResponseBody BaseResponse login(@RequestBody LoginRequest request, HttpServletRequest httpRequest) {
		ResponseStatus status = null;
		String username = null;
		String password = null;

		if (request != null) {
			username = request.getUsername();

			password = request.getPassword1();
		}

		User user = null;

		boolean isUsernameValid = isStringValid(username);
		boolean isPasswordVAlid = isStringValid(password);
		if (isUsernameValid && isPasswordVAlid) {

			user = userRepository.getUser(username, password);
			if (user != null) {
				HttpSession session = httpRequest.getSession();
				session.setAttribute(LOGGED_USER, user);
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
	public @ResponseBody BaseResponse register(@RequestBody LoginRequest request, HttpServletRequest httpRequest) {

		// if (request != null) {
		String username = request.getUsername();
		String password1 = request.getPassword1();
		String password2 = request.getPassword2();
		String email = request.getEmail();
		// }

		// TODO
		// Check if all chars are latin or digits

		BaseResponse response = new BaseResponse();
		ResponseStatus status;
		String msg = null;

		if (!isStringValid(username) || !isStringValid(password1) || !isStringValid(password2)
				|| !isStringValid(email)) {
			response.setStatus(ResponseStatus.NO);
			response.setMsg(THERE_ARE_EMPTY_FIELDS);
			return response;
		}

		if (!password1.equals(password2)) {
			response.setStatus(ResponseStatus.NO);
			response.setMsg("Passwords are different");
			return response;
		}

		if (username.length() < MIN_USERNAME_LENGTH) {

			response.setStatus(ResponseStatus.NO);
			response.setMsg("The username is too short");
			return response;
		}

		// TODO
		// VALIDATE PASSWORD FORMAT AND LENGTH

		int success = userRepository.addUser(new User(0, username, email, password1));

		if (success != 0) {
			status = ResponseStatus.OK;
			msg = REGISTRATION_SUCCESSFUL;
			String activationURL = generateActivationURL(httpRequest,username);
			new Thread() {
				public void run() {
					try {
						EmailUtil.sendEmail(email, ACTIVATION_MSG + activationURL, ACC_ACTIVATION);
					} catch (AddressException e) {
						e.printStackTrace();
					} catch (MessagingException e) {
						e.printStackTrace();
					}
				};
			}.start();

		} else {
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

		boolean isEmailValid = isStringValid(email);
		boolean isUsernameValid = isStringValid(username);

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
		
		String encryptUsername = request.getParameter("data");
		String username = EncryptionUtil.decrypt(encryptUsername);
		
		System.err.println(username);
		String returnValue = null;
		
		boolean isUsernameFree = userRepository.isValidUsername(username);
		
		
		//TODO
		if (!isUsernameFree) {
			//Update DB
			returnValue = "play";
		}else{
			returnValue = "register";
		}
		
	
		return returnValue;
	}
	private boolean isStringValid(String str) {
		boolean isValid;
		if (str != null && str.length() > 0) {
			isValid = true;
		} else {
			isValid = false;
		}
		return isValid;
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

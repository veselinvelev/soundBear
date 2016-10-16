package com.soundbear.controller;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.soundbear.model.app.User;
import com.soundbear.repository.UserDAO;
import com.soundbear.utils.AWSConstants;
import com.soundbear.utils.EncryptionUtil;
import com.soundbear.utils.ErrorMsgs;
import com.soundbear.utils.Pages;
import com.soundbear.utils.UserUtil;

@Controller
public class AccountController {
	
	@Autowired
	private UserDAO userRepository;
	@Autowired
	private HttpSession session;

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
				session.setAttribute(UserUtil.LOGGED_USER, user);
				returnValue = Pages.PLAY;

			} else {
				returnValue = Pages.LOGIN;
			}

		} else {

			returnValue = Pages.ERROR;
			request.setAttribute(ErrorMsgs.ERROR_REDIRECT, Pages.REGISTER);
			request.setAttribute(ErrorMsgs.ERROR_MSG, ErrorMsgs.ACTIVATION_FAILED);
		}

		return returnValue;
	}

	@RequestMapping(value = "/photoUpload", method = RequestMethod.POST)
	public String photoUpload(@RequestParam("photo") MultipartFile multipartFile, HttpServletRequest request) {


		User user = (User) session.getAttribute(UserUtil.LOGGED_USER);
		new Thread() {
			AmazonS3 s3client = new AmazonS3Client(new ProfileCredentialsProvider());

			@Override
			public void run() {
				InputStream is = null;

				try {
					is = multipartFile.getInputStream();
				} catch (IOException e) {
					e.printStackTrace();
				}

				String photoCloudName = ("profilepicture" + user.getUserId()
						+ ("" + LocalDateTime.now().withNano(0)).replaceAll("[T:-]", ""));

				s3client.putObject(
						new PutObjectRequest(AWSConstants.BUCKET_NAME, photoCloudName, is, new ObjectMetadata())
								.withCannedAcl(CannedAccessControlList.PublicRead));

				S3Object s3Object = s3client.getObject(new GetObjectRequest(AWSConstants.BUCKET_NAME, photoCloudName));

				String photoURL = s3Object.getObjectContent().getHttpRequest().getURI().toString();

				user.setPhoto(photoURL);
				userRepository.addPhoto(user);

				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();

		return Pages.PROFILE;
	}

}

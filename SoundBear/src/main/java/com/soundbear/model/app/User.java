package com.soundbear.model.app;

import java.util.Date;

import com.soundbear.model.app.exceptions.UserException;
import com.soundbear.utils.ValidatorUtil;

public class User {
	private int userId;
	private String username;
	private String email;
	private String password;
	private boolean isActive;
	private Date registrationDate;
	private String photo;
	private int following;
	private int followers;
	
	
	public User(int userId, String username, String email, String password, int isActive, Date registrationDate, String photo) throws UserException {
		
		setUserId(userId);
		setUsername(username);
		setEmail(email);
		setPassword(password);
		this.isActive = isActive != 0;
		if (registrationDate == null) {
			throw new UserException("Invalid Registration Data ");
		}
		this.registrationDate = registrationDate;
		
		setPhoto(photo);
		
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}



	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) throws UserException {
		if (userId < 0) {
			throw new UserException("Negative userId");
		}
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) throws UserException {
		
		if (!ValidatorUtil.isStringValid(username)) {
			throw new UserException("Invalid username");
		}
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) throws UserException {
		if (!ValidatorUtil.isStringValid(email)) {
			throw new UserException("Invalid email");
		}
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) throws UserException {
		if (!ValidatorUtil.isStringValid(password)) {
			throw new UserException("Invalid password");
		}
		
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", username=" + username + ", email=" + email + ", password=" + password
				+ "]";
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public int getFollowing() {
		return following;
	}

	public void setFollowing(int following) {
		this.following = following;
	}

	public int getFollowers() {
		return followers;
	}

	public void setFollowers(int followers) {
		this.followers = followers;
	}

}

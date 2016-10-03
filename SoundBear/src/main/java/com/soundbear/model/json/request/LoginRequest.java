package com.soundbear.model.json.request;

import java.io.Serializable;

public class LoginRequest implements Serializable {
	
	private String username;
	private String password1;
	private String password2;
	private String email;
	
	
	public String getUsername() {
		return username.trim();
	}
	public String getPassword1() {
		return password1.trim();
	}
	public String getPassword2() {
		return password2.trim();
	}

	public String getEmail() {
		return email.trim();
	}
}

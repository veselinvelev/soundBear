package com.soundbear.model.json.request;

import java.io.Serializable;

public class ResetPasswordRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5462410966761320826L;
	private String email;

	public String getEmail() {
		return email.trim();
	}

}

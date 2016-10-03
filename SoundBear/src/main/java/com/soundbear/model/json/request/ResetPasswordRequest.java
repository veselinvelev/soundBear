package com.soundbear.model.json.request;

import java.io.Serializable;

public class ResetPasswordRequest implements Serializable {

	private String email;

	public String getEmail() {
		return email.trim();
	}

}

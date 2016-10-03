package com.soundbear.model.json.reponse;

public class RegisterFormResponse extends BaseResponse {

	private boolean isValidEmail;
	private boolean isValidUsername;

	public void setValidEmail(boolean isValidEmail) {
		this.isValidEmail = isValidEmail;
	}

	public void setValidUsername(boolean isValidUsername) {
		this.isValidUsername = isValidUsername;
	}

}

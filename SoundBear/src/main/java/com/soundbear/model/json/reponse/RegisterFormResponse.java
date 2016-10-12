package com.soundbear.model.json.reponse;

public class RegisterFormResponse extends BaseResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6679040065239792073L;
	private boolean isValidEmail;
	private boolean isValidUsername;

	public void setValidEmail(boolean isValidEmail) {
		this.isValidEmail = isValidEmail;
	}

	public void setValidUsername(boolean isValidUsername) {
		this.isValidUsername = isValidUsername;
	}

}

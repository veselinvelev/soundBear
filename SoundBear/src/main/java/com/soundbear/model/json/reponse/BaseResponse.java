package com.soundbear.model.json.reponse;

import java.io.Serializable;

public class BaseResponse implements Serializable {

	private ResponseStatus status;
	private String msg;
	private String field;

	public ResponseStatus getStatus() {
		return status;
	}

	public void setStatus(ResponseStatus status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public static enum ResponseStatus {
		OK, NO;
	}
}

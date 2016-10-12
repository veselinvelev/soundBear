package com.soundbear.model.json.reponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.soundbear.model.app.User;

public class FollowersResponse extends BaseResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7363721974619897223L;
	private List<User> followers;

	public void setFollowers(List<User> followers) {
		this.followers = new ArrayList<>(followers);
	}

}

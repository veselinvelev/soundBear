package com.soundbear.model.json.reponse;

import java.util.ArrayList;
import java.util.List;

import com.soundbear.model.app.User;

public class FollowersResponse {

	private List<User> followers;

	public void setFollowers(List<User> followers) {
		this.followers = new ArrayList<>(followers);
	}

}

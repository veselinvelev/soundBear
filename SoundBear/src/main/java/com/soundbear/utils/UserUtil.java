package com.soundbear.utils;

import java.util.ArrayList;

import com.soundbear.model.app.User;
import com.soundbear.model.app.exceptions.UserException;



public class UserUtil {
	
	private static final String MASK_STR = "_";
	public static final String LOGGED_USER = "loggedUser";
	
	public static void maskPassword(ArrayList<User> users) {
		for (User user : users) {
			try {
				user.setPassword(MASK_STR);
			} catch (UserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}

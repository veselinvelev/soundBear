package com.soundbear.repository;


import java.util.List;

import com.soundbear.model.app.User;

public interface UserDAO {
	
	 public int addUser(User user);
	 
	 public User getUser(String username, String password);
	 
	 public List<User> listUsers();
	 
	 public void deleteUser(String username);
	 
	 public void updateUser(String username, User user);
	 
	 public void deleteUsers();
	 
	 public boolean isValidEmail(String email);
	 
	 public boolean isValidUsername(String username);
}
package com.soundbear.repository;

import java.util.List;

import org.springframework.stereotype.Component;

import com.soundbear.model.app.User;

@Component
public interface UserDAO {

	public int addUser(User user);

	public User getUser(String username, String password);

	public List<User> listUsers();

	public void deleteUser(String username);

	public void updateUser(User user);

	public void deleteUsers();

	public boolean isValidEmail(String email);

	public boolean isValidUsername(String username);

	public void clearInactive();

	public void updateActiveStatus(String username);

	public User getUserByName(String username);

	public void updatePassword(User user);

	public void addPhoto(String photoURL);

	public void addPhoto(User user);

	public int getfollowers(User user);

	public int getfollowing(User user);

}

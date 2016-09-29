package model.user;

import java.util.List;

import javax.sql.DataSource;

public interface UserDAO {
	public void setDataSource(DataSource dataSource);
	
	 public void addUser(User user);
	 
	 public User getUser(String username, String password);
	 
	 public List<User> listUsers();
	 
	 public void deleteUser(String username);
	 
	 public void updateUser(String username, User user);
	 
	 public void deleteUsers();
}
